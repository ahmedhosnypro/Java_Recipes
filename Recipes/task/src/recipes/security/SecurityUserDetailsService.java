package recipes.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import recipes.model.User;
import recipes.repository.UserRepository;

/**
 * This class is responsible for loading user-specific data.
 * @author Ahmed Hosny
 * @version 1.0
 * @since 2023-04-07
 */
@Getter
@Service
public class SecurityUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var optUser = userRepository.findByEmail(email);
        if (optUser.isPresent()) {
            return new SecurityUserDetails(optUser.get());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
