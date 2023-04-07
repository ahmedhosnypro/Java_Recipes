package recipes.dto;

import lombok.*;
import recipes.model.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static recipes.dto.Dto.modelMapper;

/**
 * User DTO
 * @author Ahmed Hosny
 * @version 1.0
 * @since 2023-04-07
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    @NotBlank
    @Email(regexp = ".+@.+\\..+")
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;

    /**
     * Convert UserDto to User
     * @return User
     */
    public User toUser() {
        return modelMapper().map(this, User.class);
    }
}
