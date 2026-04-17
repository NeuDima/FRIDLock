package neupokoev.com.lockservice.repository;

import neupokoev.com.lockservice.entity.Group;
import neupokoev.com.lockservice.entity.Key;
import neupokoev.com.lockservice.entity.KeyGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeyGroupRepository extends JpaRepository<KeyGroup, Integer> {

    List<KeyGroup> findAllByKey(Key key);

    boolean existsByKeyAndGroup(Key key, Group croup);

    void deleteByGroupAndKey(Group group, Key key);
}
