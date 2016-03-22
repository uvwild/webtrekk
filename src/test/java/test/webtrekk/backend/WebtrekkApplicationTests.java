package test.webtrekk.backend;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import test.webtrekk.backend.auth.JWTAuthentication;
import test.webtrekk.backend.auth.UserList;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { WebtrekkApplication.class, ApplicationSecurity.class })
abstract public class WebtrekkApplicationTests implements UserList {

    @Autowired
    private ApplicationSecurity applicationSecurity;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private WebApplicationContext context;

    //our mock mvc object for testing
    MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).addFilters(this.springSecurityFilterChain)
                .build();
    }

    public String getInvalidJWTAuthorizationHeader() {
        return "Bearer " + "blubberblubber.laberlaber.glibberglibber";
    }

    public String getValidJWTAuthorizationHeader() {
        String jwtToken = applicationSecurity.jwtAuthenticationProvider().createJWTToken(testUser);
        return "Bearer " + jwtToken;
    }

}
