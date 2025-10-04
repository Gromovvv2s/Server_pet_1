package sg.spring_pet1.controllersRest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.spring_pet1.service.JwtTokenProvider;
import sg.spring_pet1.model.dto.LoginResponse;

import static sg.spring_pet1.util.CollectionsAPI.API_LOG_IN;


@RestController
public class AuthController {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(Authentication authentication) {
        String token = jwtTokenProvider.generateToken(authentication.getName());
        System.out.println("token be generated: " + token);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        String userName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        System.out.println("userName from SecurityContextHolder after login: " + userName);
        return ResponseEntity.ok(loginResponse);
    }
}
