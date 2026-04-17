package neupokoev.com.lockservice.dto;

import neupokoev.com.lockservice.entity.Group;

public record LockReadDto(Integer id, String name, String secret, Group group) {
}
