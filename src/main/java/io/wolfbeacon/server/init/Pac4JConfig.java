package io.wolfbeacon.server.init;

import io.wolfbeacon.server.model.Account;
import io.wolfbeacon.server.security.gitkit.GitkitAuthenticator;
import io.wolfbeacon.server.security.gitkit.GitkitClient;
import io.wolfbeacon.server.security.jwt.HeaderTokenClient;
import io.wolfbeacon.server.security.jwt.TimedJwtAuthenticator;
import io.wolfbeacon.server.security.jwt.TimedJwtGenerator;
import io.wolfbeacon.server.service.GitKitIdentityService;
import org.pac4j.core.authorization.authorizer.Authorizer;
import org.pac4j.core.authorization.authorizer.RequireAllRolesAuthorizer;
import org.pac4j.core.config.Config;
import org.pac4j.core.profile.CommonProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aaron on 20/04/2016.
 */
public class Pac4JConfig {
    private static final String PROPERTY_NAME_JWT_SIGNING_SECRET = "jwt.signing.secret";
    private static final String PROPERTY_NAME_JWT_ENCRYPTION_SECRET = "jwt.encryption.secret";

    @Resource
    private Environment environment;
    @Autowired
    private GitKitIdentityService gitKitIdentityService;

    @Bean
    private TimedJwtGenerator<CommonProfile> timedJwtGenerator() {
        return new TimedJwtGenerator<>(environment.getProperty(PROPERTY_NAME_JWT_SIGNING_SECRET),
                environment.getProperty(PROPERTY_NAME_JWT_ENCRYPTION_SECRET));
    }

    @Bean
    public Config pac4JConfig() {
        Map<String, Authorizer> authorizers = new HashMap<>();
        authorizers.put("admin", new RequireAllRolesAuthorizer<>(Account.ROLE_ADMIN, Account.ROLE_USER));
        authorizers.put("user", new RequireAllRolesAuthorizer<>(Account.ROLE_USER));

        return new Config(authorizers, new HeaderTokenClient("access_token",
                new TimedJwtAuthenticator(environment.getProperty(PROPERTY_NAME_JWT_SIGNING_SECRET),
                        environment.getProperty(PROPERTY_NAME_JWT_ENCRYPTION_SECRET))),
                new GitkitClient("gtoken", new GitkitAuthenticator(gitKitIdentityService)));
    }
}
