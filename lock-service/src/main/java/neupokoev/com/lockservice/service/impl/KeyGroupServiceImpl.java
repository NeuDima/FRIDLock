package neupokoev.com.lockservice.service.impl;

import lombok.RequiredArgsConstructor;
import neupokoev.com.lockservice.entity.Group;
import neupokoev.com.lockservice.entity.Key;
import neupokoev.com.lockservice.entity.KeyGroup;
import neupokoev.com.lockservice.repository.KeyGroupRepository;
import neupokoev.com.lockservice.service.KeyGroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KeyGroupServiceImpl implements KeyGroupService {

    private final KeyGroupRepository keyGroupRepository;

    @Override
    public List<KeyGroup> getAllByKey(Key key) {
        return keyGroupRepository.findAllByKey(key);
    }

    @Override
    public boolean existsByKeyAndGroup(Key key, Group group) {
        return keyGroupRepository.existsByKeyAndGroup(key, group);
    }

    @Transactional
    @Override
    public void addKeyToGroup(Group group, Key key) {
        var newKeyGroup = KeyGroup.builder()
                .key(key)
                .group(group)
                .build();
        keyGroupRepository.save(newKeyGroup);
    }

    @Transactional
    @Override
    public void deleteKeyFromGroup(Group group, Key key) {
        keyGroupRepository.deleteByGroupAndKey(group, key);
    }
}
