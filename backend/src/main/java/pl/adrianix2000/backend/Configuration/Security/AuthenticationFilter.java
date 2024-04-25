package pl.adrianix2000.backend.Configuration.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.adrianix2000.backend.Models.Entities.User;
import pl.adrianix2000.backend.Services.JWTService;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        log.info(authorizationHeader);

        if(authorizationHeader != null) {
            if(authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(6).trim();

                log.info(token);

                try {
                    SecurityContextHolder.getContext().setAuthentication(
                            jwtService.verifyToken(token));

                } catch (RuntimeException e) {
                    SecurityContextHolder.clearContext();
                    throw e;
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
