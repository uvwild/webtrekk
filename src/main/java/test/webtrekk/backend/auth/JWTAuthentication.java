package test.webtrekk.backend.auth;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * the implementation ofthe spring security authentication interface including the JWT token
 */
public class JWTAuthentication implements Authentication {

    public static final String ROLES_CLAIM_NAME = "roles";

    public static final String X_AUTH_TOKEN_HEADER = "X-AuthToken";
    public static final String AUTHORIZATION_HEADER = "Authorization";

    protected String tokenString;
    protected Claims tokenClaims;
    protected List<GrantedAuthority> authorities;
    protected boolean authenticated;


    public JWTAuthentication(String jwtToken) {
        tokenString = jwtToken;
        authorities = new ArrayList<>(0);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getDetails() {
        return tokenClaims.toString();
    }

    @Override
    public Object getPrincipal() {
        return tokenClaims.getSubject();
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return tokenClaims.getSubject();
    }

    public String getToken() {
        return tokenString;
    }

    public void setTokenClaims(Claims tokenClaims) {
        this.tokenClaims = tokenClaims;
        Collection roles = this.tokenClaims.get(ROLES_CLAIM_NAME, Collection.class);
        if (null != roles) {
            ArrayList<GrantedAuthority> authsList = new ArrayList<>(roles.size());
            for (Object role : roles) {
                authsList.add(new SimpleGrantedAuthority(role.toString()));
            }
            authorities = Collections.unmodifiableList(authsList);
        } else {
            authorities = Collections.emptyList();
        }
    }
}
