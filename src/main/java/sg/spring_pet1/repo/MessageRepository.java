package sg.spring_pet1.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sg.spring_pet1.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
