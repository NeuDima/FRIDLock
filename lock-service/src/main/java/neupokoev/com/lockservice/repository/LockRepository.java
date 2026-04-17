package neupokoev.com.lockservice.repository;

import neupokoev.com.lockservice.entity.Lock;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface LockRepository extends JpaRepository<Lock, Integer> {

    Optional<Lock> findByName(String name);
}
