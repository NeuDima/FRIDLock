package neupokoev.com.lockservice.security.service;

import lombok.RequiredArgsConstructor;
import neupokoev.com.lockservice.dto.TypeUser;
import neupokoev.com.lockservice.repository.LockRepository;
import neupokoev.com.lockservice.security.customDetails.CustomLockDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomLockServiceImpl implements TypedUserDetailsService {

    private final LockRepository lockRepository;

    @Override
    public TypeUser getType() {
        return TypeUser.LOCK_DEVICE;
    }

    @Override
    public CustomLockDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return lockRepository.findByName(username)
                .map(CustomLockDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
