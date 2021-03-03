package pl.edu.pg.eti.hang.University.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.eti.hang.University.digest.Sha256Utility;
import pl.edu.pg.eti.hang.University.user.entity.User;
import pl.edu.pg.eti.hang.University.user.service.UserService;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

/**
 * Listener started automatically on CDI application context initialized. Injects proxy to the services and fills
 * database with default content. When using persistence storage application instance should be initialized only during
 * first run in order to init database with starting data. Good place to create first default admin user.
 */
@Component
public class InitializedData {

    /**
     * Service for users operations.
     */
    private final UserService userService;


    @Autowired
    public InitializedData(UserService userService) {
        this.userService = userService;
    }

    /**
     * Initializes database with some example values. Should be called after creating this object. This object should
     * be created only once.
     */
    @PostConstruct
    private synchronized void init() {
        User admin = User.builder()
                .login("admin")
                .name("Alvin")
                .surname("Liu")
                .birthDate(LocalDate.of(1985, 1, 1))
                .email("s179216@student.pg.edu.pl")
                .password(Sha256Utility.hash("admin"))
                .build();

        User kevin = User.builder()
                .login("kevin")
                .name("Kevin")
                .surname("Pear")
                .birthDate(LocalDate.of(2001, 1, 16))
                .email("kevin@example.com")
                .password(Sha256Utility.hash("useruser"))
                .build();

        User alice = User.builder()
                .login("alice")
                .name("Alice")
                .surname("Grape")
                .birthDate(LocalDate.of(2002, 3, 19))
                .email("alice@example.com")
                .password(Sha256Utility.hash("useruser"))
                .build();

        userService.create(admin);
        userService.create(kevin);
        userService.create(alice);
    }

}
