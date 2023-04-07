package recipes.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import recipes.dto.UserDto;
import recipes.model.User;
import recipes.security.SecurityUserDetailsService;

/**
 * Controller for user registration
 * @author Ahmed Hosny
 * @version 1.0
 * @since 2023-04-07
 */
@RestController
public class UserController {
    private final SecurityUserDetailsService securityUserDetailsService;

    @Autowired
    public UserController(SecurityUserDetailsService securityUserDetailsService) {
        this.securityUserDetailsService = securityUserDetailsService;
    }

    /**
     * Register new user
     * @param userDto - user data
     * @return - 200 (OK) if user successfully registered,
     * 400 (Bad Request) if user with this email already exists or required fields are invalid
     */
    @PostMapping("/api/register")
    public ResponseEntity<Object> save(@RequestBody @Valid UserDto userDto) {
        User user = userDto.toUser();
        if (securityUserDetailsService.existsByEmail(user.getEmail())) {
            throw new UserExistsException("User with this email already exists");
        } else {
            securityUserDetailsService.save(user);
            return ResponseEntity.ok().build();
        }
    }
}

/**
 * Exception for user already exists
 * @author Ahmed Hosny
 * @version 1.0
 * @since 2023-04-07
 */
class UserExistsException extends RuntimeException {
    public UserExistsException(String message) {
        super(message);
    }
}
