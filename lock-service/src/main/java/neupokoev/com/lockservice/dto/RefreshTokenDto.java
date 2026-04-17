package neupokoev.com.lockservice.dto;

import lombok.Data;

@Data
public class RefreshTokenDto {

    private TypeUser type;
    private String refreshToken;
}
