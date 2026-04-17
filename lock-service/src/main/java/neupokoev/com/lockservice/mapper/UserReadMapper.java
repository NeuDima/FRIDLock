package neupokoev.com.lockservice.mapper;

import neupokoev.com.lockservice.dto.UserReadDto;
import neupokoev.com.lockservice.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserReadMapper implements Mapper<User, UserReadDto> {

    @Override
    public UserReadDto map(User user) {
        return new UserReadDto(user.getId(), user.getName(), user.getPassword());
    }
}
