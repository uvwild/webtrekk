package test.webtrekk.backend;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @RequestMapping(value = "/home", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
    public ResponseEntity<?> home() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<String>("Hello "+ authentication.getName(),HttpStatus.OK);
    }
}
