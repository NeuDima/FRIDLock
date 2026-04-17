package neupokoev.com.lockservice.service.impl;

import lombok.RequiredArgsConstructor;
import neupokoev.com.lockservice.entity.Group;
import neupokoev.com.lockservice.repository.GroupRepository;
import neupokoev.com.lockservice.service.GroupService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    @Override
    public Group getGroupById(Integer groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new NullPointerException("Group not found by id " + groupId));
    }
}
