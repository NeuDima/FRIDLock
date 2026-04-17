package neupokoev.com.lockservice.repository;

import neupokoev.com.lockservice.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Integer> {
}
