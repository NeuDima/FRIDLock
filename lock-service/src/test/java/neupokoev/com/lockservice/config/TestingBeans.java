package neupokoev.com.lockservice.config;

import neupokoev.com.lockservice.security.jwt.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestingBeans {

    @Bean
    public JwtService jwtService() {
        return new JwtService();
    }
}
