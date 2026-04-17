package neupokoev.com.lockservice.mapper;

import lombok.RequiredArgsConstructor;
import neupokoev.com.lockservice.dto.LockCreateDto;
import neupokoev.com.lockservice.dto.UserCreateDto;
import neupokoev.com.lockservice.entity.Lock;
import neupokoev.com.lockservice.entity.User;
import neupokoev.com.lockservice.service.GroupService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LockCreateMapper implements Mapper<LockCreateDto, Lock> {

    private final PasswordEncoder passwordEncoder;
    private final GroupService groupService;

    @Override
    public Lock map(LockCreateDto lockCreateDto) {
        Lock lock = new Lock();
        return copy(lockCreateDto, lock);
    }

    @Override
    public Lock map(LockCreateDto lockCreateDto, Lock lock) {
        return copy(lockCreateDto, lock);
    }

    private Lock copy(LockCreateDto lockCreateDto, Lock lock) {
        lock.setName(lockCreateDto.name());
        lock.setSecret(passwordEncoder.encode(lockCreateDto.secret()));
        lock.setGroup(groupService.getGroupById(lockCreateDto.groupId()));
        return lock;
    }
}
