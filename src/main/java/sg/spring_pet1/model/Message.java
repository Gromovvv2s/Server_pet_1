package sg.spring_pet1.model;

import jakarta.persistence.*;
import lombok.*;
import sg.spring_pet1.model.security.Person;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String data;
    private LocalDateTime date;
    private String usernameFrom;
    private String usernameTo;
    @ManyToMany(mappedBy = "messages")
    private List<Person> persons;
}
