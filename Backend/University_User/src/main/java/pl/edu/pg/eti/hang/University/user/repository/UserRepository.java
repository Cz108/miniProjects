package pl.edu.pg.eti.hang.University.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pg.eti.hang.University.user.entity.User;

import java.util.Optional;

/**
 * Repository for User entity. Repositories should be used in business layer (e.g.: in services).
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Seeks for single user using login and password. Can be use in authentication module.
     *
     * @param login    user's login
     * @param password user's password (hash)
     * @return container (can be empty) with user
     */
    Optional<User> findByLoginAndPassword(String login, String password);

}
