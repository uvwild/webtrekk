package test.webtrekk.backend;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import test.webtrekk.backend.auth.JWTAuthentication;

import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by uv on 21.03.2016 for webtrekk
 */
@WebAppConfiguration
public class HomeControllerTest extends WebtrekkApplicationTests {

    @Test
    public void testGetNoJWT() throws Exception {
        mvc.perform(get("/home").accept(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetWrongJWT() throws Exception {
        mvc.perform(get("/home").header(JWTAuthentication.AUTHORIZATION_HEADER, getInvalidJWTAuthorizationHeader()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetJohnDoeJWT() throws Exception {
        MvcResult result = mvc.perform(get("/home").header(JWTAuthentication.AUTHORIZATION_HEADER, getValidJWTAuthorizationHeader()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assert (result.getResponse().getContentAsString().contains(testUser));
//        System.err.println(ToStringBuilder.reflectionToString(result));
    }

    @Test
    public void testPostJohnDoeJWT() throws Exception {
        MvcResult result = mvc.perform(post("/home").header(JWTAuthentication.AUTHORIZATION_HEADER, getValidJWTAuthorizationHeader()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn(); assert (result.getResponse().getContentAsString().contains(testUser));
    }


}
