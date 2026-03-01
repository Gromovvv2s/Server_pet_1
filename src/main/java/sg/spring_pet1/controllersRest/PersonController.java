package sg.spring_pet1.controllersRest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.spring_pet1.model.security.Person;
import sg.spring_pet1.repo.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static sg.spring_pet1.util.CollectionsAPI.PERSON_FRIENDS;

@RestController
public class PersonController {
    @Autowired
    private PersonRepository personRepository;
    @GetMapping(value = PERSON_FRIENDS, produces = {"application/json"})
    public ResponseEntity<List<String>> getFriends() {
        List<String> namesFriends = new ArrayList<>();
        try {
            String nameCurrentUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            Person currentPerson = personRepository.findByUsername(nameCurrentUser);
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
