package sg.spring_pet1.controllersRest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.spring_pet1.model.security.Person;
import sg.spring_pet1.repo.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/person/{id}")
public class PersonController {
    @Autowired
    private PersonRepository personRepository;
    @GetMapping("/friends")
    public ResponseEntity<List<String>> getFriends(@PathVariable Long id) {
        //костыль.
        // в дальнейшем будем брать текущего пользователя из контекста.
        List<String> namesFriends = new ArrayList<>();
        try {
            Person currentPerson = personRepository.findById(id).get();
            namesFriends = currentPerson.getIdsFriends().stream()
                .map(idFriend -> {
                    Optional<Person> currentFriend = personRepository.findById(idFriend);
                    return currentFriend.isPresent() ? currentFriend.get().getUsername() : "null";
                }).collect(Collectors.toList());
        } catch (Throwable e) {
            System.out.println("API getFriends error!!!");
        }
        return ResponseEntity.ok(namesFriends);
    }
}
