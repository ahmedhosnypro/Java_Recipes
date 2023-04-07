package recipes.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import recipes.model.User;

import java.util.Collection;
import java.util.Collections;

/**
 * <div>
 * <p>This class is used to wrap the User object and provide the UserDetails interface.</p>
 * <p>This is needed because the User object does not implement UserDetails.</p>
 * <p>The UserDetails interface is used by Spring Security to authenticate users.</p>
 * </div>
 * @author Ahmed Hosny
 * @version 1.0
 * @since 2023-04-07
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SecurityUserDetails implements UserDetails {
    private transient User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
