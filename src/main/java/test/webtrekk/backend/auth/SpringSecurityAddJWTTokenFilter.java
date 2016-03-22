package test.webtrekk.backend.auth;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by uv on 21.03.2016 for webtrekk
 */
public class SpringSecurityAddJWTTokenFilter extends GenericFilterBean {

    protected JWTAuthenticationProvider jwtAuthenticationProvider;

    public SpringSecurityAddJWTTokenFilter(JWTAuthenticationProvider jwtAuthenticationProvider) {
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }

    /**
     * this filter adds the JWT token as a response header if called using basic auth
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        Authentication createdAuth = SecurityContextHolder.getContext().getAuthentication();
        if (null != createdAuth && createdAuth.isAuthenticated()) {
            // Fallthrough when called with a JWT: the X_AUTH_TOKEN_HEADER is already set and the principal is just a String not a User
            if (createdAuth.getPrincipal() instanceof User) {
                User theUser = (User)createdAuth.getPrincipal();
                if (null != theUser.getUsername()) {
                    String jwtToken = jwtAuthenticationProvider.createJWTToken(theUser.getUsername());
                    httpResponse.setHeader(JWTAuthentication.X_AUTH_TOKEN_HEADER, jwtToken);
                    // we need the x auth token in the request header....
                }
            } else {
                if(logger.isDebugEnabled()) {
                    logger.debug("authorized Principal is not of User Type: " + createdAuth.getPrincipal().getClass().toString());
                }
            }
        }
        chain.doFilter(request, response);
    }
}
