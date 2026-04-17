package neupokoev.com.lockservice.service;

import neupokoev.com.lockservice.entity.Group;
import neupokoev.com.lockservice.entity.Key;
import neupokoev.com.lockservice.entity.KeyGroup;

import java.util.List;

public interface KeyGroupService {

    List<KeyGroup> getAllByKey(Key key);

    boolean existsByKeyAndGroup(Key key, Group group);

    void addKeyToGroup(Group group, Key key);

    void deleteKeyFromGroup(Group group, Key key);
}
