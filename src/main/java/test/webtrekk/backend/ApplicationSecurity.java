package test.webtrekk.backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import test.webtrekk.backend.auth.JWTAuthenticationProvider;
import test.webtrekk.backend.auth.SpringSecurityAddJWTTokenFilter;
import test.webtrekk.backend.auth.SpringSecurityJWTAuthenticationFilter;
import test.webtrekk.backend.auth.UserList;

/**
 * Created by uv on 21.03.2016 for webtrekk
 */
@Configuration
@EnableWebSecurity  // @EnableGlobalMethodSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER)
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class ApplicationSecurity extends WebSecurityConfigurerAdapter implements UserList {

    @Value("${JWT_SECRET:defaultSecret}")
    protected String secret;

    @Override  // this overrides default config in super
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()               // http://stackoverflow.com/questions/19468209/spring-security-403-error
                .httpBasic()                    // use basic auth to auhenticate (Authorization header)
                .and()
                .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/home").authenticated()
                    .antMatchers(HttpMethod.GET, "/home").authenticated()
                    .antMatchers(HttpMethod.POST, "/login").hasRole(userRole)
                    .antMatchers(HttpMethod.GET, "/login").hasRole(userRole)
        ;
        http.addFilterBefore(new SpringSecurityJWTAuthenticationFilter(super.authenticationManagerBean()),
                             BasicAuthenticationFilter.class);
        http.addFilterAfter(new SpringSecurityAddJWTTokenFilter(jwtAuthenticationProvider()),
                            BasicAuthenticationFilter.class);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth    .authenticationProvider(jwtAuthenticationProvider())        // configure our specific authentication provider for JWT
                .inMemoryAuthentication()
                .withUser(testUser).password(testPassword).roles(userRole)
//                .and()
//                .withUser(testUser2).password(testPassword2).roles(userRole)
        ;
    }

    @Bean
    public JWTAuthenticationProvider jwtAuthenticationProvider() {
        return new JWTAuthenticationProvider(secret);
    }

}
