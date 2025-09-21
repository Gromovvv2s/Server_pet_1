package sg.spring_pet1.config.provaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import sg.spring_pet1.model.aut.JwtAuthenticationToken;
import sg.spring_pet1.repo.PersonRepository;

@Component
public class JwtAutProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("11111");
        //хуй пойми что
        return new JwtAuthenticationToken(authentication.getName(), authentication.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // Указываем, какие типы Authentication этот провайдер поддерживает
        return authentication.equals(JwtAuthenticationToken.class);
    }
}
