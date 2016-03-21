package test.webtrekk.backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import test.webtrekk.backend.auth.JWTAuthenticationProvider;
import test.webtrekk.backend.auth.SpringSecurityAddJWTTokenFilter;
import test.webtrekk.backend.auth.SpringSecurityJWTAuthenticationFilter;

/**
 * Created by uv on 21.03.2016 for webtrekk
 */
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

    @Value("${JWT_SECRET:defaultSecret}")
    protected String secret;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated().and()
                .httpBasic().and()
                .csrf().disable();
        http.addFilterBefore(new SpringSecurityJWTAuthenticationFilter(super.authenticationManagerBean()),
                             BasicAuthenticationFilter.class);
        http.addFilterAfter(new SpringSecurityAddJWTTokenFilter(jwtAuthenticationProvider()),
                            BasicAuthenticationFilter.class);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(jwtAuthenticationProvider())
                .inMemoryAuthentication().withUser("user").password("user").roles("USER");
    }

    @Bean
    public JWTAuthenticationProvider jwtAuthenticationProvider() {
        return new JWTAuthenticationProvider(secret);
    }

}
