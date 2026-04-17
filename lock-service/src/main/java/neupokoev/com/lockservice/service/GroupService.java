package neupokoev.com.lockservice.service;

import neupokoev.com.lockservice.entity.Group;

import java.util.Optional;

public interface GroupService {

    Group getGroupById(Integer groupId);
}
