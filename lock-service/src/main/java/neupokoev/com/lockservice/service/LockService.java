package neupokoev.com.lockservice.service;

import neupokoev.com.lockservice.dto.JwtAuthenticationDto;
import neupokoev.com.lockservice.dto.LockCreateDto;
import neupokoev.com.lockservice.dto.LockReadDto;
import neupokoev.com.lockservice.dto.RefreshTokenDto;
import neupokoev.com.lockservice.entity.Lock;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.util.Optional;

public interface LockService {

    Optional<Lock> getLockByName(String name);

    JwtAuthenticationDto signIn(LockCreateDto lockCreateDto) throws AuthenticationException;

    JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) throws AuthenticationException;

    @Transactional
    LockReadDto addLock(LockCreateDto lockCreateDto);
}
