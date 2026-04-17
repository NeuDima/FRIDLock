package neupokoev.com.lockservice.service;

import neupokoev.com.lockservice.dto.*;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;

public interface UserService {

    JwtAuthenticationDto signIn(UserCreateDto userCreateDto) throws AuthenticationException;

    JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) throws AuthenticationException;

    @Transactional
    UserReadDto addUser(UserCreateDto userCreateDto);
}
