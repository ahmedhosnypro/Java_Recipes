package recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import recipes.model.User;

import java.util.Optional;

/**
 * This interface is used to access the database and perform CRUD operations on the User table.
 * @author Ahmed Hosny
 * @version 1.0
 * @since 2023-04-07
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
