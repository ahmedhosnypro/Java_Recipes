package recipes.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Security configuration class for the application.
 *
 * @author Ahmed Hosny
 * @version 1.0
 * @since 2023-04-07
 */
@Configuration
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        return new SecurityUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Security filter chain for the application.
     * <p>
     *     allowed requests without authentication:
     *     <ul>
     *         <li>post request to /api/register</li>
     *         <li>post request to /actuator/shutdown</li>
     *         <li>get request to /h2-console/**</li>
     *     </ul>
     * </p>
     * <p>
     *     <b>NOTE:</b>
     *     <ul>
     *         <li>csrf is disabled.</li>
     *         <li>frame options are disabled.</li>
     *         <li>headers are disabled.</li>
     *         <li>http basic authentication is used.</li>
     *     </ul>
     * </p>
     *
     * @param http HttpSecurity object
     * @return SecurityFilterChain object
     * @throws Exception Exception if any error occurs
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeHttpRequests(authorize -> {
                    authorize.antMatchers("/api/register").permitAll();
                    authorize.antMatchers("/actuator/shutdown").permitAll();
                    authorize.antMatchers("/h2-console/**").permitAll();
                    authorize.anyRequest().authenticated();
                })
                .httpBasic(withDefaults())
                .authenticationProvider(authenticationProvider())
                .build();
    }
}
