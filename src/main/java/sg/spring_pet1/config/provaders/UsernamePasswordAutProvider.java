package sg.spring_pet1.config.provaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import sg.spring_pet1.model.security.Person;
import sg.spring_pet1.repo.PersonRepository;
import sg.spring_pet1.service.security.PersonService;

@Component
public class UsernamePasswordAutProvider implements AuthenticationProvider {
    @Autowired
    private PersonService personService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("start UsernamePasswordAutProvider process!");
        UserDetails userDetailsPerson = personService.loadUserByUsername(authentication.getPrincipal().toString());
        System.out.println("finish UsernamePasswordAutProvider process!");
        return new UsernamePasswordAuthenticationToken(authentication.getPrincipal().toString(), userDetailsPerson.getPassword(), userDetailsPerson.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // Указываем, какие типы Authentication этот провайдер поддерживает
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
