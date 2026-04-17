package neupokoev.com.lockservice.security.service;

import neupokoev.com.lockservice.dto.TypeUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface TypedUserDetailsService extends UserDetailsService {

    TypeUser getType();
}