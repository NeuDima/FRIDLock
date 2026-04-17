package neupokoev.com.lockservice.mapper;

import neupokoev.com.lockservice.dto.LockReadDto;
import neupokoev.com.lockservice.dto.UserReadDto;
import neupokoev.com.lockservice.entity.Lock;
import neupokoev.com.lockservice.entity.User;
import org.springframework.stereotype.Component;

@Component
public class LockReadMapper implements Mapper<Lock, LockReadDto> {

    @Override
    public LockReadDto map(Lock lock) {
        return new LockReadDto(lock.getId(),
                lock.getName(),
                lock.getSecret(),
                lock.getGroup());
    }
}
