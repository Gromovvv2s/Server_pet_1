package sg.spring_pet1.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sg.spring_pet1.model.security.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
}
