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
import java.util.Set;

import static sg.spring_pet1.util.CollectionsAPI.API_LOG_IN;

public class JwtAutFilter extends OncePerRequestFilter {
    private static final Set<String> NEED_SPACE_URLS = Set.of(API_LOG_IN);
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    public JwtAutFilter(JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String currentUrl = request.getRequestURI();
        if (!NEED_SPACE_URLS.contains(currentUrl)) {
            System.out.println("start process JwtAutFilter by url= " + currentUrl);
            final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                System.out.println("JwtAutFilter: invalid authHeader " + authHeader);
                filterChain.doFilter(request, response);
                return;
            }
            final String jwt = authHeader.substring(7); // "Bearer ".length() = 7
            final String username = jwtTokenProvider.extractUsername(jwt);
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
                } else  {
                    System.out.println("error process JwtAutFilter by url= " + currentUrl);
                    System.out.println("token is invalid!");
                }
            } else {
                System.out.println("error process JwtAutFilter by url= " + currentUrl);
                System.out.println("username= " + username + " Authentication= " + SecurityContextHolder.getContext().getAuthentication());
            }
            System.out.println("finish process JwtAutFilter by url= " + currentUrl);
        } else {
            System.out.println("space process JwtAutFilter by url= " + currentUrl);
        }
        filterChain.doFilter(request, response);
    }
}
