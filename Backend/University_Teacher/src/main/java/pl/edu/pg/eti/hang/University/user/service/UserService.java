package pl.edu.pg.eti.hang.University.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.hang.University.user.entity.User;
import pl.edu.pg.eti.hang.University.user.repository.UserRepository;

import java.util.Optional;


/**
 * Service layer for all business actions regarding user entity.
 */
@Service
public class UserService {

    /**
     * Mock of the database. Should be replaced with repository layer.
     */
    private UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    /**
     * @param login user's login
     * @return container with user
     */
    public Optional<User> find(String login) {
        return repository.findById(login);
    }

    /**
     * Stores new user in the storage.
     *
     * @param user new user
     */
    @Transactional
    public void create(User user) {
        repository.save(user);
    }

    /**
     * Deletes selected user.
     *
     * @param user user to be deleted
     */
    @Transactional
    public void delete(User user) {
        repository.delete(user);
    }

}
