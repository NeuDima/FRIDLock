package neupokoev.com.lockservice.security.customDetails;

import neupokoev.com.lockservice.entity.Lock;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public record CustomLockDetails(Lock lock) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return lock.getSecret();
    }

    @Override
    public String getUsername() {
        return lock.getName();
    }
}
