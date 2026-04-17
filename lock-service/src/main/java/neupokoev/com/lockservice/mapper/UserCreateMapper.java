package neupokoev.com.lockservice.mapper;

import lombok.RequiredArgsConstructor;
import neupokoev.com.lockservice.dto.UserCreateDto;
import neupokoev.com.lockservice.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCreateMapper implements Mapper<UserCreateDto, User> {

    private final PasswordEncoder passwordEncoder;

    @Override
    public User map(UserCreateDto userCreateDto) {
        User user = new User();
        return copy(userCreateDto, user);
    }

    @Override
    public User map(UserCreateDto userCreateDto, User user) {
        return copy(userCreateDto, user);
    }

    private User copy(UserCreateDto userCreateDto, User user) {
        user.setName(userCreateDto.name());
        user.setPassword(passwordEncoder.encode(userCreateDto.password()));
        return user;
    }
}
