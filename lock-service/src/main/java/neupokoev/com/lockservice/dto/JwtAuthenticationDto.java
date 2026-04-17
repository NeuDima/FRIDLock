package neupokoev.com.lockservice.dto;

import lombok.Data;

@Data
public class JwtAuthenticationDto {

    private TypeUser type;
    private String accessToken;
    private String refreshToken;
}
