package neupokoev.com.lockservice.service.impl;

import lombok.RequiredArgsConstructor;
import neupokoev.com.lockservice.dto.*;
import neupokoev.com.lockservice.entity.User;
import neupokoev.com.lockservice.mapper.UserCreateMapper;
import neupokoev.com.lockservice.mapper.UserReadMapper;
import neupokoev.com.lockservice.repository.UserRepository;
import neupokoev.com.lockservice.security.jwt.JwtService;
import neupokoev.com.lockservice.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserCreateMapper userCreateMapper;
    private final UserReadMapper userReadMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    private final TypeUser typeUser = TypeUser.USER;

    @Override
    public JwtAuthenticationDto signIn(UserCreateDto userCreateDto) throws AuthenticationException {
        User user = findByCreateDto(userCreateDto);
        return jwtService.generateAuthToken(user.getName(), typeUser);
    }

    @Override
    public JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) throws AuthenticationException {
        String refreshToken = refreshTokenDto.getRefreshToken();

        if (refreshToken != null && jwtService.validateJwtToken(refreshToken)) {
            User user = findByUsername(jwtService.getUsernameFromToken(refreshToken));
            return jwtService.refreshBaseToken(user.getName(), refreshToken, typeUser);
        }
        throw new AuthenticationException("Invalid refresh token");
    }

    @Override
    @Transactional
    public UserReadDto addUser(UserCreateDto userCreateDto) {
        return Optional.ofNullable(userCreateDto)
                .map(userCreateMapper::map)
                .map(userRepository::save)
                .map(userReadMapper::map)
                .orElseThrow();
    }

    private User findByCreateDto(UserCreateDto userCreateDto) throws AuthenticationException {
        return userRepository.findByName(userCreateDto.name())
                .filter(user -> passwordEncoder.matches(
                        userCreateDto.password(),
                        user.getPassword()))
                .orElseThrow(() -> new AuthenticationException("Username or password is not correct"));
    }

    private User findByUsername(String username) {
        return userRepository
                .findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
