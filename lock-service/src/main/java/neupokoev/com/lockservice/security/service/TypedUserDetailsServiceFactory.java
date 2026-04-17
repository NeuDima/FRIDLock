package neupokoev.com.lockservice.security.service;

import neupokoev.com.lockservice.dto.TypeUser;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TypedUserDetailsServiceFactory {

    private final Map<TypeUser, TypedUserDetailsService> serviceMap;

    public TypedUserDetailsServiceFactory(List<TypedUserDetailsService> services) {

        serviceMap = services.stream()
                .collect(Collectors.toMap(
                        TypedUserDetailsService::getType,
                        Function.identity()
                ));
    }

    public TypedUserDetailsService getService(TypeUser type) {
        return serviceMap.get(type);
    }
}
