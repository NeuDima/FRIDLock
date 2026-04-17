package neupokoev.com.lockservice.controller;

import lombok.RequiredArgsConstructor;
import neupokoev.com.lockservice.dto.JwtAuthenticationDto;
import neupokoev.com.lockservice.dto.LockCreateDto;
import neupokoev.com.lockservice.dto.RefreshTokenDto;
import neupokoev.com.lockservice.dto.UserCreateDto;
import neupokoev.com.lockservice.service.LockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("lock-service-api/auth/lock/")
@RequiredArgsConstructor
public class AuthLockController {

    private final LockService lockService;

    @PostMapping("sign-in")
    public ResponseEntity<JwtAuthenticationDto> signIn(@RequestBody LockCreateDto lockCreateDto) {
        try {
            JwtAuthenticationDto jwtAuthenticationDto = lockService.signIn(lockCreateDto);
            return ResponseEntity.ok(jwtAuthenticationDto);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Authentication failed " + e.getMessage());
        }
    }

    @PostMapping("refresh")
    public JwtAuthenticationDto refresh(@RequestBody RefreshTokenDto refreshTokenDto) throws AuthenticationException {
        return lockService.refreshToken(refreshTokenDto);
    }
}
