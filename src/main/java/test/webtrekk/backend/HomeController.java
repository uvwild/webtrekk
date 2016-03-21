package test.webtrekk.backend;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import test.webtrekk.backend.auth.JWTAuthentication;

/**
 * Created by uv on 20.03.2016 for webtrekk
 */
@RestController
@EnableWebSecurity
public class HomeController {

    @PreAuthorize("authenticated")
    @RequestMapping(value = "/home", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> home(@RequestHeader(value=JWTAuthentication.X_AUTH_HEADER) String x_auth_header) {
        if (x_auth_header.isEmpty())
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity("Hello Jon Doe",HttpStatus.OK);
    }
}
