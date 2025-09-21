package sg.spring_pet1.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sg.spring_pet1.model.dto.UserDto;
import sg.spring_pet1.model.security.Person;
import sg.spring_pet1.model.security.Role;
import sg.spring_pet1.repo.PersonRepository;

import java.util.Collections;

@Service
public class RegistrationService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PersonService userDetailsService;

    public boolean registerUser(UserDto userDto) {
        // 1. Проверка, что имя пользователя или email не заняты
        if (personRepository.findByUsername(userDto.getUsername()) != null) {
            //throw new IllegalArgumentException("Username already taken");
            return false;
        }
        // 2. Создание нового пользователя
        Person person = new Person();
        person.setUsername(userDto.getUsername());
        // 3. Хэширование пароля
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        person.setPassword(encodedPassword);
        person.setRoles(Collections.singletonList(new Role(1L,"USER")));

        personRepository.save(person);

        UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return true;
    }
}
