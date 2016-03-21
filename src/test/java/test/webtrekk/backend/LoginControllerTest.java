package test.webtrekk.backend;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import test.webtrekk.backend.auth.JWTAuthentication;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by uv on 20.03.2016 for webtrekk
 */
@WebAppConfiguration
public class LoginControllerTest extends WebtrekkApplicationTests {

    String basicDigestHeaderValue = "Basic " + new String(Base64.encodeBase64(("johndoe:mysecretpassword").getBytes()));

    @Test
    public void testNoAuth() throws Exception {
        mvc.perform(get("/login"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testJohnDoeJWT() throws Exception {
        mvc.perform(get("/login").header(JWTAuthentication.X_AUTH_HEADER, computeValidJWTtestToken()))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testBasicAuth() throws Exception {
        MvcResult result = mvc.perform(get("/login").header("Authorization", basicDigestHeaderValue).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertThat( result, null);
    }

}
