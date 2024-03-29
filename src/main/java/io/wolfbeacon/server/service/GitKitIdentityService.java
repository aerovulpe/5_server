package io.wolfbeacon.server.service;

import com.google.gson.JsonObject;
import com.google.identitytoolkit.GitkitClient;
import com.google.identitytoolkit.GitkitClientException;
import com.google.identitytoolkit.GitkitServerException;
import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;
import io.wolfbeacon.server.model.Account;
import io.wolfbeacon.server.security.gitkit.GitKitProfile;
import io.wolfbeacon.server.web.exception.InternalServerErrorException;
import io.wolfbeacon.server.web.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Created by Aaron on 03/05/2016.
 */
@Service
public class GitKitIdentityService {
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private UserAccountManager userAccountManager;
    @Autowired
    private SendGrid sendGrid;
    private GitkitClient GITKIT_CLIENT;

    {
        try {
            GITKIT_CLIENT = GitkitClient.createFromJson(getClass().getClassLoader()
                    .getResource("gitkit-server-config.json").getPath().substring(1));
        } catch (GitkitClientException | IOException e) {
            e.printStackTrace();
        }
    }

    public GitKitIdentityService() {
    }

    //Gitkit Widget
    public void handleOauthCallback(HttpServletRequest request,
                                    HttpServletResponse response) {
        response.setContentType("text/html");

        StringBuilder builder = new StringBuilder();
        String line;
        try {
            while ((line = request.getReader().readLine()) != null) {
                builder.append(line);
            }
            String postBody = URLEncoder.encode(builder.toString(), "UTF-8");
            response.getWriter().print(new Scanner(new File(servletContext.getRealPath("/static/gitkit-widget.html")), "UTF-8")
                    .useDelimiter("\\A").next()
                    .replaceAll("JAVASCRIPT_ESCAPED_POST_BODY", postBody));
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new NotFoundException();
        } catch (IOException e) {
            e.printStackTrace();
            throw new InternalServerErrorException();
        }
    }

    //Email Endpoint
    public void sendEmail(HttpServletRequest request, HttpServletResponse response) {
        try {
            GitkitClient.OobResponse oobResponse = GITKIT_CLIENT.getOobResponse(request);

            String subject;
            String text;
            if (oobResponse.getOobAction().equals(GitkitClient.OobAction.CHANGE_EMAIL)) {
                subject = "Email address change for Spring Boilerplate account";
                text = "Hello!\n\n The email address for your Spring Boilerplate account will be changed from "
                        + oobResponse.getEmail() + " to " + oobResponse.getNewEmail() +
                        " when you click this confirmation link:\n\n " + oobResponse.getOobUrl().get() +
                        "\n\nIf you didn't request an email address change for this account, please disregard this message.";
                sendEmail(oobResponse.getEmail(), subject, text);
            } else if (oobResponse.getOobAction().equals(GitkitClient.OobAction.RESET_PASSWORD)) {
                subject = "Password change for Spring Boilerplate account";
                text = "Hello!\n\n The password for your Spring Boilerplate account will be reset " +
                        "when you click this confirmation link:\n\n " + oobResponse.getOobUrl().get() +
                        "\n\nIf you didn't request a password change for this account, please disregard this message.";
                sendEmail(oobResponse.getEmail(), subject, text);
            }
            response.getWriter().write(oobResponse.getResponseBody());
        } catch (IOException | SendGridException | GitkitServerException e) {
            throw new InternalServerErrorException();
        }
    }

    private void sendEmail(String recipientEmail, String subject, String text)
            throws SendGridException {
        SendGrid.Email email = new SendGrid.Email();
        email.addTo(recipientEmail);
        email.setFrom("aerisvulpe@gmail.com");
        email.setSubject(subject);
        email.setText(text);
        sendGrid.send(email);
    }

    public String getUserLocalId(HttpServletRequest request) {
        JsonObject gitkitUserPayload = getUserPayload(getAuthTokenFromRequest(request));
        if (gitkitUserPayload == null) {
            return null;
        }

        return gitkitUserPayload.get("user_id").getAsString();
    }

    private JsonObject getUserPayload(String authToken) {
        try {
            return GITKIT_CLIENT.validateTokenToJson(authToken);
        } catch (GitkitClientException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getAuthTokenFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if ("gtoken".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }

    public GitKitProfile getGitKitProfile(HttpServletRequest request, boolean updateAccount) {
        return getGitKitProfile(getAuthTokenFromRequest(request), updateAccount);
    }

    public GitKitProfile getGitKitProfile(String gtoken, boolean updateAccount) {
        JsonObject gitkitUserPayload = getUserPayload(gtoken);
        if (gitkitUserPayload == null) {
            return null;
        }

        String userId = gitkitUserPayload.get("user_id").getAsString();
        String email = gitkitUserPayload.get("email").getAsString();
        boolean verified = gitkitUserPayload.get("verified").getAsBoolean();
        Account account = userAccountManager.getAccountByNaturalId(userId);
        boolean newAccount = account == null;

        if (!newAccount && !updateAccount) {
            return gitKitProfileFromAccount(account);
        }

        if (newAccount) {
            account = new Account();
        }
        if (gitkitUserPayload.has("display_name")) {
            String[] names = gitkitUserPayload.get("display_name").getAsString().split(" ");
            account.setFirstName(names[0]);
            if (names.length > 1) {
                account.setLastName(names[names.length - 1]);
            } else if (newAccount) {
                account.setLastName("");
            }
        } else if (newAccount) {
            account.setFirstName("");
            account.setLastName("");
        }
        account.setNaturalId(userId);
        if (gitkitUserPayload.has("photo_url")) {
            account.setPictureUrl(gitkitUserPayload.get("photo_url").getAsString());
        }
        account.setEmail(email);
        if (verified) {
            account.addPermission(Account.PERMISSION_EMAIL_VERIFIED);
        }

        if (newAccount) {
            account.addRole(Account.ROLE_USER);
            account.addPermission(Account.PERMISSION_ENABLED);
            if (!verified) {
                try {
                    sendVerificationEmail(email, GITKIT_CLIENT.getEmailVerificationLink(email));
                } catch (SendGridException | GitkitServerException | GitkitClientException e) {
                    e.printStackTrace();
                }
            }
            userAccountManager.createNewUserAccount(account);
        } else {
            userAccountManager.updateAccount(account);
        }

        return gitKitProfileFromAccount(account);
    }

    private GitKitProfile gitKitProfileFromAccount(Account account) {
        final GitKitProfile profile = new GitKitProfile();
        profile.setId(account.getNaturalId());
        profile.addAttribute("account_id", account.getId());
        profile.addAttribute("email", account.getEmail());
        profile.addAttribute("first_name", account.getFirstName());
        profile.addAttribute("family_name", account.getLastName());
        profile.addAttribute("name", account.getFirstName() + " " + account.getLastName());
        profile.addAttribute("display_name", account.getFirstName());
        profile.addAttribute("picture_url", account.getPictureUrl());
        profile.addRoles(new ArrayList<>(account.getRoles()));
        profile.addPermissions(new ArrayList<>(account.getPermissions()));
        return profile;
    }

    private void sendVerificationEmail(String recipientEmail, String emailVerificationLink)
            throws SendGridException {
        String subject = "Verify email address for Spring Boilerplate account";
        String text = "Hello!\n\n The email address for your Spring Boilerplate account needs to be verified. " +
                "Please click this confirmation link:\n\n " + emailVerificationLink +
                "\n\nIf you didn't sign up with this email address, please disregard this message.";

        sendEmail(recipientEmail, subject, text);
    }
}
