package sg.spring_pet1.config.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import sg.spring_pet1.model.dto.LoginRequest;

import java.io.IOException;
import java.util.Set;

import static sg.spring_pet1.util.CollectionsAPI.API_LOG_IN;

public class UsernamePasswordAutFilter extends OncePerRequestFilter {
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper; // Для чтения JSON из тела запроса
    private static final Set<String> URLS_NEED_FILTER = Set.of(API_LOG_IN);

    public UsernamePasswordAutFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String currentUrl = request.getRequestURI();
        if (URLS_NEED_FILTER.contains(currentUrl)) {
            System.out.println("start process filter request UsernamePasswordAuthenticationFilter by url = " + currentUrl);
            try {
                LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
                System.out.println("loginRequest: " + loginRequest);
                UsernamePasswordAuthenticationToken usernamePasswordAuthToken = new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                );
                // Аутентифицируем пользователя
                Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthToken);
                System.out.println("authentication: " + authentication);
                // Устанавливаем аутентификацию в SecurityContextHolder
                SecurityContextHolder.getContext().setAuthentication(authentication);
                // Успешная аутентификация - продолжаем цепочку фильтров
                System.out.println("finish process filter request UsernamePasswordAuthenticationFilter by url = " + currentUrl);
                filterChain.doFilter(request, response);
            } catch (AuthenticationException e) {
                // Аутентификация не удалась
                System.out.println("error process filter request UsernamePasswordAuthenticationFilter by url = " + currentUrl);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Authentication failed: " + e.getMessage());
            }
        } else {
            System.out.println("space process filter request UsernamePasswordAuthenticationFilter by url = " + currentUrl);
            filterChain.doFilter(request, response);
        }
    }

}

