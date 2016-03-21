package test.webtrekk.backend;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import test.webtrekk.backend.auth.JWTAuthentication;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by uv on 21.03.2016 for webtrekk
 */
@WebAppConfiguration
public class HomeControllerTest extends WebtrekkApplicationTests {

    @Test
    public void testNoJWT() throws Exception {
        mvc.perform(get("/home"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testWrongJWT() throws Exception {
        mvc.perform(get("/home").header(JWTAuthentication.X_AUTH_HEADER, inValidJWTtestToken()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testJohnDoeJWT() throws Exception {
        mvc.perform(get("/home").header(JWTAuthentication.X_AUTH_HEADER, computeValidJWTtestToken()))
                .andExpect(status().is2xxSuccessful());
    }

}
