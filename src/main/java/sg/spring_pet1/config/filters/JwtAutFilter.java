package sg.spring_pet1.config.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import sg.spring_pet1.model.aut.JwtAuthenticationToken;
import sg.spring_pet1.service.JwtTokenProvider;

import java.io.IOException;

public class JwtAutFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    public JwtAutFilter(JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (false) {
            System.out.println("start JwtAutFilter");
            final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            final String jwt;
            String username;
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                System.out.println("JwtAutFilter: invalid authHeader " + authHeader);
                filterChain.doFilter(request, response);
                return;
            }
            jwt = authHeader.substring(7); // "Bearer ".length() = 7
            username = jwtTokenProvider.extractUsername(jwt);
            System.out.println("username from jwt = " + username);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (jwtTokenProvider.isTokenValid(jwt)) {
                    //этот JwtAuthenticationToken бы проверить, что с ним все ок и так можно
                    //мб сюда токен передавать, а там с ним разбираться.
                    JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(
                            username
                    );
                    //это зачем?
                    jwtAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    Authentication authentication = authenticationManager.authenticate(jwtAuthenticationToken);

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            System.out.println("finish JwtAutFilter");
        } else {
            System.out.println("space JwtAutFilter");
        }
        filterChain.doFilter(request, response);
    }
}
