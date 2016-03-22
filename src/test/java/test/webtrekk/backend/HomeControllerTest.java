package test.webtrekk.backend;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import test.webtrekk.backend.auth.JWTAuthentication;

import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by uv on 21.03.2016 for webtrekk
 */
@WebAppConfiguration
public class HomeControllerTest extends WebtrekkApplicationTests {

    @Test
    public void testNoJWT() throws Exception {
        mvc.perform(get("/home").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testWrongJWT() throws Exception {
        mvc.perform(get("/home").header(JWTAuthentication.X_AUTH_HEADER, inValidJWTtestToken()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testJohnDoeJWT() throws Exception {
        MvcResult result = mvc.perform(get("/home").header(JWTAuthentication.X_AUTH_HEADER, computeValidJWTtestToken()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful()).andReturn();
        // todo check result
    }

}
