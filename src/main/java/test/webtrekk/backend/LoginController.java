package test.webtrekk.backend;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import test.webtrekk.backend.auth.JWTAuthentication;

/**
 * Created by uv on 20.03.2016 for webtrekk
 */
@RestController
@EnableWebSecurity
public class LoginController {

    @PreAuthorize("authenticated")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(String login, String password) {
        RestTemplate restTemplate = new RestTemplate();
        JWTAuthentication jwtAuthentication = restTemplate.getForObject("/login/jwt", JWTAuthentication.class);
        return new ResponseEntity<JWTAuthentication>(jwtAuthentication, HttpStatus.CREATED);
    }
}
