package pl.adrianix2000.backend.Configuration.Security;

import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.el.parser.Token;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

        String requestURI = request.getRequestURI();

        log.info(requestURI);
        if(authorizationHeader != null) {
            if(authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(6).trim();

                log.info(token);

                try {
                    if(!requestURI.equals("/cryptoPosts/addPost")) {
                        SecurityContextHolder.getContext().setAuthentication(
                                jwtService.verifyToken(token));
                    } else {
                        if(jwtService.getClaimFromToken(token, "role").equals("ADMIN")) {
                            SecurityContextHolder.getContext().setAuthentication(
                                    jwtService.verifyToken(token));
                        }
                    }
                }
                catch (TokenExpiredException ex) {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.setHeader(HttpHeaders.WWW_AUTHENTICATE, "TOKEN EXPIRED");
                }
                catch (RuntimeException e) {
                    SecurityContextHolder.clearContext();
                    throw e;
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
