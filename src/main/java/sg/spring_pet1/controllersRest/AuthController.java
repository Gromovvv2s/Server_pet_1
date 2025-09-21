package sg.spring_pet1.controllersRest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.spring_pet1.service.JwtTokenProvider;
import sg.spring_pet1.model.dto.LoginResponse;

import static sg.spring_pet1.util.CollectionsAPI.API_LOG_IN;


@RestController
public class AuthController {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping(API_LOG_IN)
    public ResponseEntity<LoginResponse> login(Authentication authentication) {
        String token = jwtTokenProvider.generateToken(authentication.getName());
        System.out.println("token be generated: " + token);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        return ResponseEntity.ok(loginResponse);
    }
}
