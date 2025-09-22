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

import static sg.spring_pet1.util.CollectionsAPI.API_LOG_IN;

public class UsernamePasswordAutFilter extends OncePerRequestFilter {
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper; // Для чтения JSON из тела запроса

    public UsernamePasswordAutFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //в конфиг файле переписать перехват по нужным url
        if (!request.getRequestURI().equals(API_LOG_IN)) {
            System.out.println("space UsernamePasswordAuthenticationFilter");
            filterChain.doFilter(request, response); // Пропускаем запросы, которые не соответствуют /login. есть только POST
            return;
        }
        System.out.println("start UsernamePasswordAuthenticationFilter");
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
            System.out.println("finish UsernamePasswordAuthenticationFilter");
            filterChain.doFilter(request, response);
        } catch (AuthenticationException e) {
            // Аутентификация не удалась
            System.out.println("error UsernamePasswordAuthenticationFilter");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Authentication failed: " + e.getMessage());
        }
    }

}

