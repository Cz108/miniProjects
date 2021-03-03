package pl.edu.pg.eti.hang.University.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.hang.University.digest.Sha256Utility;
import pl.edu.pg.eti.hang.University.user.entity.User;
import pl.edu.pg.eti.hang.University.user.event.repository.UserEventRepository;
import pl.edu.pg.eti.hang.University.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for all business actions regarding user entity.
 */
@Service
public class UserService {

    /**
     * Repository for user management.
     */
    private UserRepository repository;

    /**
     * Repository for sending events about actions on user entities.
     */
    private UserEventRepository eventRepository;

    /**
     *
     * @param repository repository for user management
     * @param eventRepository repository for sending events about actions on user entities
     */
    @Autowired
    public UserService(UserRepository repository, UserEventRepository eventRepository) {
        this.repository = repository;
        this.eventRepository = eventRepository;
    }

    /**
     * @param login user's login
     * @return container with user
     */
    public Optional<User> find(String login) {
        return repository.findById(login);
    }

    /**
     * Seeks for single user using login and password. Can be use in authentication module.
     *
     * @param login    user's login
     * @param password user's password (hash)
     * @return container (can be empty) with user
     */
    public Optional<User> find(String login, String password) {
        return repository.findByLoginAndPassword(login, Sha256Utility.hash(password));
    }

    /**
     * Stores new user in the storage.
     *
     * @param user new user
     */
    @Transactional
    public void create(User user) {
        repository.save(user);
        eventRepository.create(user);
    }

    @Transactional
    public void update(User user) {
        repository.save(user);
    }

    /**
     * @return all available users
     */
    public List<User> findAll() {
        return repository.findAll();
    }

    /**
     * Deletes selected user.
     *
     * @param user user to be deleted
     */
    @Transactional
    public void delete(User user) {
        eventRepository.delete(user);
        repository.delete(user);
    }

}
