package sg.spring_pet1.controllersRest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.spring_pet1.model.Message;
import sg.spring_pet1.model.dto.MessageDto;
import sg.spring_pet1.model.security.Person;
import sg.spring_pet1.repo.MessageRepository;
import sg.spring_pet1.repo.PersonRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/message")
//позже перенести в нужные контроллеры, лишнее убрать, что-то объединить
// вызовы сделать через сервисы, а не репозитории
public class MessageController {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private MessageRepository messageRepository;
    @PostMapping("/send/{idFrom}/{idTo}")
    public ResponseEntity<HttpStatus> sendMessage(@RequestBody MessageDto messageDto, @PathVariable Long idFrom, @PathVariable Long idTo) {
        HttpStatus result;
        //должно произойти в рамках одной транзакции!
        //позже добавить сервис + @Transactional
        try {
            Message message = new Message();
            message.setData(messageDto.getMessage());
            messageRepository.save(message);

            Person personFrom = personRepository.findById(idFrom).get();
            personFrom.getMessages().add(message);
            Person personTo = personRepository.findById(idTo).get();
            personTo.getMessages().add(message);

            personRepository.saveAll(List.of(personFrom, personTo));
            result = HttpStatus.OK;
        } catch (Throwable e) {
            System.out.println("api sendMessage error!!!");
            result = HttpStatus.METHOD_NOT_ALLOWED;
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/send/{nameFrom}/{nameTo}")
    public ResponseEntity<HttpStatus> sendMessage(@RequestBody MessageDto messageDto,@PathVariable String nameFrom, @PathVariable String nameTo) {
        HttpStatus result;
        //должно произойти в рамках одной транзакции!
        //позже добавить сервис + @Transactional
        try {
            Message message = new Message();
            message.setData(messageDto.getMessage());
            message.setDate(LocalDateTime.now());
            message.setUsernameFrom(nameFrom);
            message.setUsernameTo(nameTo);
            messageRepository.save(message);

            Person personFrom = personRepository.findByUsername(nameFrom);
            personFrom.getMessages().add(message);
            Person personTo = personRepository.findByUsername(nameTo);
            personTo.getMessages().add(message);

            personRepository.saveAll(List.of(personFrom, personTo));
            result = HttpStatus.OK;
        } catch (Throwable e) {
            System.out.println("api sendMessage error!!!");
            result = HttpStatus.METHOD_NOT_ALLOWED;
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/get/{idPerson}/{idFriend}")
    public ResponseEntity<List<MessageDto>> getMessagesPersonByIds(@PathVariable Long idPerson, @PathVariable Long idFriend) {
        List<MessageDto> result = new ArrayList<>();
        try {
            Person person = personRepository.findById(idPerson).get();
            Person friend = personRepository.findById(idFriend).get();
            result = getMessages(person, friend);
        } catch (Throwable e) {
            System.out.println("api getMessagesPerson error!!!");
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/get/{namePerson}/{nameFriend}")
    public ResponseEntity<List<MessageDto>> getMessagesPersonByNames(@PathVariable String namePerson, @PathVariable String nameFriend) {
        List<MessageDto> result = new ArrayList<>();
        try {
            Person person = personRepository.findByUsername(namePerson);
            Person friend = personRepository.findByUsername(nameFriend);
            result = getMessages(person, friend);
        } catch (Throwable e) {
            System.out.println("api getMessagesPerson error!!!");
        }
        return ResponseEntity.ok(result);
    }

    private List<MessageDto> getMessages(Person currentPerson, Person friend) {
        List<MessageDto> result;
        //скорей всего есть лучше вариант хранения
        // мб отдельная таблица для переписки, чтоб не бежать по всем сообщениям этого пользователя.
        // через @Query или EntityManager делать операции с этой таблицей

        // предусмотреть Pageble
        result = currentPerson.getMessages().stream()
        //отберем все сообщения этого пользователя
        //которые ему отправил друг или он отправил другу
        .filter(message ->
            message.getUsernameFrom().equals(friend.getUsername())
            || message.getUsernameTo().equals(friend.getUsername())
        )
        .sorted(Comparator.comparing(Message::getDate).reversed())

        .map(message -> new MessageDto(message.getData(), message.getUsernameFrom()))
        .collect(Collectors.toList());

        return result;
    }
}
