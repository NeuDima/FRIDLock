package neupokoev.com.lockservice.repository;

import neupokoev.com.lockservice.entity.Key;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeyRepository extends JpaRepository<Key, Integer> {

    Optional<Key> findByUid(String uid);
}
