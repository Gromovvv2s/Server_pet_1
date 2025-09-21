package sg.spring_pet1.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sg.spring_pet1.model.security.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByUsername(String firstName);
}
