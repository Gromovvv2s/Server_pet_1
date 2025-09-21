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
        //тут вроде должны в бд сгонять или нет я зх бл
        // тут сгоняем в бд и проверим.
        // а мб и не тут, пока не понял, где
        // ии грит что тут, пробуем!
        Person person = personRepository.findByUsername(authentication.getPrincipal().toString());
        System.out.printf("111");
        return new UsernamePasswordAuthenticationToken(authentication.getName(), null, authentication.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        // Указываем, какие типы Authentication этот провайдер поддерживает
        return authentication.equals(UsernamePasswordAuthenticationToken.class); // Пример для логина/пароля
        // или JwtAuthenticationToken.class для JWT
    }
}
