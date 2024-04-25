package pl.adrianix2000.backend.Configuration.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.security.Security;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig {

    @Bean
    public SecurityFilterChain configureSecurityOfRequests(HttpSecurity securityChain) throws Exception {
        return securityChain
                .authorizeHttpRequests(httpRequest ->
                        httpRequest.requestMatchers("/users/getAll").permitAll()
                                .anyRequest().authenticated())
                .build();
    }
}
