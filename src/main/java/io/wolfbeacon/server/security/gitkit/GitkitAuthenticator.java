package io.wolfbeacon.server.security.gitkit;

import io.wolfbeacon.server.service.GitKitIdentityService;
import org.pac4j.core.credentials.TokenCredentials;
import org.pac4j.core.credentials.authenticator.TokenAuthenticator;
import org.pac4j.core.exception.CredentialsException;
import org.pac4j.core.exception.RequiresHttpAction;

/**
 * Created by Aaron on 05/05/2016.
 */
public class GitkitAuthenticator implements TokenAuthenticator {

    private final GitKitIdentityService gitKitIdentityService;

    public GitkitAuthenticator(GitKitIdentityService gitKitIdentityService) {
        this.gitKitIdentityService = gitKitIdentityService;
    }

    @Override
    public void validate(TokenCredentials credentials) throws RequiresHttpAction {
        GitKitProfile profile;
        try {
            profile = gitKitIdentityService.getGitKitProfile(credentials.getToken(), false);
        } catch (Exception e) {
            throw new CredentialsException("Could not authenticate user.", e);
        }

        if (profile == null) {
            throw new CredentialsException("User not authenticated.");
        }

        credentials.setUserProfile(profile);
    }
}
