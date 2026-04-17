package neupokoev.com.lockservice.security;

import neupokoev.com.lockservice.dto.TypeUser;
import neupokoev.com.lockservice.security.service.TypedUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class AuthServiceConfig {

    @Bean
    public Map<TypeUser, TypedUserDetailsService> userDetailsServices(List<TypedUserDetailsService> services) {

        return services.stream()
                .collect(Collectors.toMap(
                        TypedUserDetailsService::getType,
                        Function.identity()
                ));
    }
}
