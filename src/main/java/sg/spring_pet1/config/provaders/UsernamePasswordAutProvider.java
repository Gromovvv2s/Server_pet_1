package sg.spring_pet1.config.provaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import sg.spring_pet1.model.security.Person;
import sg.spring_pet1.repo.PersonRepository;

@Component
public class UsernamePasswordAutProvider implements AuthenticationProvider {
    @Autowired
    private PersonRepository personRepository;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("start UsernamePasswordAutProvider process!");
        Person person = personRepository.findByUsername(authentication.getPrincipal().toString());
        System.out.println("finish UsernamePasswordAutProvider process!");
        return new UsernamePasswordAuthenticationToken(authentication.getName(), null, authentication.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        // Указываем, какие типы Authentication этот провайдер поддерживает
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
