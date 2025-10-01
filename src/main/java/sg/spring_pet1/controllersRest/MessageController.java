package sg.spring_pet1.controllersRest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.spring_pet1.model.Message;
import sg.spring_pet1.model.dto.MessageDto;
import sg.spring_pet1.model.security.Person;
import sg.spring_pet1.repo.MessageRepository;
import sg.spring_pet1.repo.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MessageController {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private MessageRepository messageRepository;
    @PostMapping("send/message/{idTo}")
    public ResponseEntity<HttpStatus> sendMessage(@RequestBody MessageDto messageDto, @PathVariable Long idTo) {
        HttpStatus result;

        try {
            Person person = personRepository.findById(idTo).get();
            Message message = new Message();
            message.setData(messageDto.getMessage());
            messageRepository.save(message);
            person.getMessages().add(message);
            personRepository.save(person);
            result = HttpStatus.OK;
        } catch (Throwable e) {
            System.out.println("api sendMessage error!!!");
            result = HttpStatus.METHOD_NOT_ALLOWED;
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("get/messages/{idPerson}")
    public ResponseEntity<List<MessageDto>> getMessagesPerson(@PathVariable Long idPerson) {
        List<MessageDto> result = new ArrayList<>();
        try {
            result = personRepository.findById(idPerson).get().getMessages().stream()
                    .map(m -> new MessageDto(m.getData(), m.getUsernameFrom()))
                    .collect(Collectors.toList());
        } catch (Throwable e) {
            System.out.println("api getMessagesPerson error!!!");
        }
        return ResponseEntity.ok(result);
    }
}
