package test.webtrekk.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import test.webtrekk.backend.auth.JWTAuthentication;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by uv on 20.03.2016 for webtrekk
 */
@RestController
public class LoginController {

    @RequestMapping(value = "/login", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json")
    public ResponseEntity<?> doLogin(HttpServletRequest httpServletRequest) {
//        RestTemplate restTemplate = new RestTemplate();
//        JWTAuthentication jwtAuthentication = restTemplate.getForObject("//login/jwt", JWTAuthentication.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.err.println(authentication);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
