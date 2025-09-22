package sg.spring_pet1.config.provaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import sg.spring_pet1.model.aut.JwtAuthenticationToken;
import sg.spring_pet1.service.security.PersonService;

@Component
public class JwtAutProvider implements AuthenticationProvider {
    @Autowired
    private PersonService personService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("JwtAutProvider start process!");
        UserDetails userDetailsPerson = personService.loadUserByUsername(authentication.getPrincipal().toString());
        //пока просто достали у него права, но ничего не проверяли.
        System.out.println("JwtAutProvider finish process!");
        return new JwtAuthenticationToken(authentication.getPrincipal().toString(), userDetailsPerson.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // Указываем, какие типы Authentication этот провайдер поддерживает
        return authentication.equals(JwtAuthenticationToken.class);
    }
}
