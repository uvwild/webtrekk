package test.webtrekk.backend;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import test.webtrekk.backend.auth.JWTAuthentication;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by uv on 20.03.2016 for webtrekk
 */
@WebAppConfiguration
public class LoginControllerTest extends WebtrekkApplicationTests {

    @Test
    public void testGetNoAuth() throws Exception {
        mvc     .perform(get("/login").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testPostNoAuth() throws Exception {
        mvc     .perform(post("/login").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testJohnDoeJWT() throws Exception {
        mvc     .perform(get("/login").header(JWTAuthentication.X_AUTH_HEADER, computeValidJWTtestToken()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testBasicAuthLogin() throws Exception {
        MvcResult result = mvc
                .perform(get("/login").with(httpBasic(testUser, testPassword)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertNotNull(result);
    }

    @Test
    public void testBasicAuthLogin2() throws Exception {
        MvcResult result = mvc
                .perform(get("/login").with(httpBasic(testUser2, testPassword2)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertNotNull(result);
    }

}
