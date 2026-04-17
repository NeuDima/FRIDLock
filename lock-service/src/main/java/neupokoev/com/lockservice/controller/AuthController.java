package neupokoev.com.lockservice.controller;

import lombok.RequiredArgsConstructor;
import neupokoev.com.lockservice.dto.JwtAuthenticationDto;
import neupokoev.com.lockservice.dto.RefreshTokenDto;
import neupokoev.com.lockservice.dto.UserCreateDto;
import neupokoev.com.lockservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("lock-service-api/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("sign-in")
    public ResponseEntity<JwtAuthenticationDto> signIn(@RequestBody UserCreateDto userCreateDto) {
        try {
            JwtAuthenticationDto jwtAuthenticationDto = userService.signIn(userCreateDto);
            return ResponseEntity.ok(jwtAuthenticationDto);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Authentication failed " + e.getMessage());
        }
    }

    @PostMapping("refresh")
    public JwtAuthenticationDto refresh(@RequestBody RefreshTokenDto refreshTokenDto) throws AuthenticationException {
        return userService.refreshToken(refreshTokenDto);
    }
}
