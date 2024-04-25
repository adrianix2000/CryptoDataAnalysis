package pl.adrianix2000.backend.Configuration.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.adrianix2000.backend.Models.Entities.User;

import java.io.IOException;
import java.util.Collections;

@Slf4j
public class AuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        log.info("sdfsdfsdf");

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(User.builder().build(),
                        null, Collections.emptyList())
        );
        filterChain.doFilter(request, response);
    }
}
