package neupokoev.com.lockservice.service.impl;

import lombok.RequiredArgsConstructor;
import neupokoev.com.lockservice.dto.*;
import neupokoev.com.lockservice.entity.Lock;
import neupokoev.com.lockservice.mapper.LockCreateMapper;
import neupokoev.com.lockservice.mapper.LockReadMapper;
import neupokoev.com.lockservice.repository.LockRepository;
import neupokoev.com.lockservice.security.jwt.JwtService;
import neupokoev.com.lockservice.service.LockService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LockServiceImpl implements LockService {

    private final LockRepository lockRepository;
    private final LockCreateMapper lockCreateMapper;
    private final LockReadMapper lockReadMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    private final TypeUser typeLock = TypeUser.LOCK_DEVICE;

    @Override
    public Optional<Lock> getLockByName(String name) {
        return lockRepository.findByName(name);
    }

    @Override
    public JwtAuthenticationDto signIn(LockCreateDto lockCreateDto) throws AuthenticationException {
        Lock lock = findByCreateDto(lockCreateDto);
        return jwtService.generateAuthToken(lock.getName(), typeLock);
    }

    @Override
    public JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) throws AuthenticationException {
        String refreshToken = refreshTokenDto.getRefreshToken();

        if (refreshToken != null && jwtService.validateJwtToken(refreshToken)) {
            Lock lock = findByName(jwtService.getUsernameFromToken(refreshToken));
            return jwtService.refreshBaseToken(lock.getName(), refreshToken, typeLock);
        }
        throw new AuthenticationException("Invalid refresh token");
    }

    @Transactional
    @Override
    public LockReadDto addLock(LockCreateDto lockCreateDto) {
        return Optional.ofNullable(lockCreateDto)
                .map(lockCreateMapper::map)
                .map(lockRepository::save)
                .map(lockReadMapper::map)
                .orElseThrow();
    }

    private Lock findByCreateDto(LockCreateDto lockCreateDto) throws AuthenticationException {
        return lockRepository.findByName(lockCreateDto.name())
                .filter(lock -> passwordEncoder.matches(
                        lockCreateDto.secret(),
                        lock.getSecret()))
                .orElseThrow(() -> new AuthenticationException("Name or secret is not correct"));
    }

    private Lock findByName(String username) {
        return lockRepository
                .findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Lock not found"));
    }
}
