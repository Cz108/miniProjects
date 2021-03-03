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

}
