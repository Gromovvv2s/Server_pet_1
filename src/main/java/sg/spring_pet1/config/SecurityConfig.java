package sg.spring_pet1.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import sg.spring_pet1.config.filters.JwtAutFilter;
import sg.spring_pet1.config.filters.UsernamePasswordAutFilter;
import sg.spring_pet1.service.JwtTokenProvider;

import java.util.Arrays;
import java.util.List;

import static sg.spring_pet1.util.CollectionsAPI.API_LOG_IN;
import static sg.spring_pet1.util.CollectionsAPI.API_TEST;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    /*
    возможно, что аутентификация по jwt на данный момент не будет знать о роли юзера.
    смотри класс JwtAuthenticationToken
     */
    //спринг сам соберет все провайдеры сюда.
    @Autowired
    private List<AuthenticationProvider> authenticationProviders;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationConfiguration authenticationConfiguration) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Enable CORS and configure it
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, API_LOG_IN).permitAll()
                        .requestMatchers(HttpMethod.GET, API_TEST).permitAll()
                        .anyRequest().authenticated() // Все остальные запросы требуют аутентификации
                )
                .addFilterBefore(usernamePasswordAutFilter(authenticationConfiguration.getAuthenticationManager(), objectMapper()),  UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAutFilter(jwtTokenProvider, authenticationConfiguration.getAuthenticationManager()),  UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    UsernamePasswordAutFilter usernamePasswordAutFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
        return new UsernamePasswordAutFilter(authenticationManager,  objectMapper);
    }

    @Bean
    JwtAutFilter jwtAutFilter(JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        return new JwtAutFilter(jwtTokenProvider, authenticationManager);
    }

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    //тут еще подразобраться
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Используем BCrypt для хеширования паролей
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // Пример origins
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Разрешить все методы
        configuration.setAllowedHeaders(List.of("*")); // Разрешить все заголовки
        configuration.setAllowCredentials(false); // Разрешить передачу credentials (например, cookies)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Применить эту конфигурацию ко всем путям
        return source;
    }
}
