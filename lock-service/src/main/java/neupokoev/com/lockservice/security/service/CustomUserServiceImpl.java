package neupokoev.com.lockservice.security.service;

import lombok.RequiredArgsConstructor;
import neupokoev.com.lockservice.dto.TypeUser;
import neupokoev.com.lockservice.repository.UserRepository;
import neupokoev.com.lockservice.security.customDetails.CustomUserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserServiceImpl implements TypedUserDetailsService {

    private final UserRepository userRepository;

    @Override
    public TypeUser getType() {
        return TypeUser.USER;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByName(username)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
