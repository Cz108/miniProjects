package pl.edu.pg.eti.hang.University.configuration;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.eti.hang.University.teacher.entity.Teacher;
import pl.edu.pg.eti.hang.University.teacher.entity.Direction;
import pl.edu.pg.eti.hang.University.teacher.service.TeacherService;
import pl.edu.pg.eti.hang.University.teacher.service.DirectionService;
import pl.edu.pg.eti.hang.University.user.entity.User;
import pl.edu.pg.eti.hang.University.user.service.UserService;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.time.LocalDate;


/**
 * Listener started automatically on CDI application context initialized. Injects proxy to the services and fills
 * database with default content. When using persistence storage application instance should be initialized only during
 * first run in order to init database with starting data. Good place to create first default admin user.
 */
@Component
public class InitializedData {

    /**
     * Service for teachers operations.
     */
    private final TeacherService teacherService;

    /**
     * Service for users operations.
     */
    private final UserService userService;

    /**
     * Service for directions operations.
     */
    private final DirectionService directionService;

    @Autowired
    public InitializedData(TeacherService teacherService, UserService userService, DirectionService directionService) {
        this.teacherService = teacherService;
        this.userService = userService;
        this.directionService = directionService;
    }

    /**
     * Initializes database with some example values. Should be called after creating this object. This object should
     * be created only once.
     */
    @PostConstruct
    private synchronized void init() {
        User admin = User.builder()
                .login("admin")
                .build();

        User kevin = User.builder()
                .login("kevin")
                .build();

        User alice = User.builder()
                .login("alice")
                .build();

        userService.create(admin);
        userService.create(kevin);
        userService.create(alice);

        Direction Electronics = Direction.builder().name("Electronics").build();
        Direction Algorithms = Direction.builder().name("Algorithms").build();
        Direction Software = Direction.builder().name("Software").build();
        Direction Geographic = Direction.builder().name("Geographic").build();

        directionService.create(Electronics);
        directionService.create(Algorithms);
        directionService.create(Software);
        directionService.create(Geographic);

        Teacher Bob = Teacher.builder()
                .name("Bob")
                .sex("M")
                .age(45)
                .background("A strict teacher with great kindness")
                .direction(Electronics)
                .portrait(getResourceAsByteArray("avatar/calvian.png"))//package relative path
                .user(kevin)
                .build();

        Teacher Jakub = Teacher.builder()
                .name("Jakub")
                .sex("M")
                .age(60)
                .background("A greate teacher with a lot of high quoted essays")
                .direction(Algorithms)
                .portrait(getResourceAsByteArray("avatar/uhlbrecht.png"))//package relative path
                .user(kevin)
                .build();

        Teacher Banhoff = Teacher.builder()
                .name("Banhoff")
                .sex("M")
                .age(32)
                .background("He is good at software engineering")
                .direction(Software)
                .portrait(getResourceAsByteArray("avatar/eloise.png"))//package relative path
                .user(alice)
                .build();

        Teacher Sebastian = Teacher.builder()
                .name("Sebastian")
                .sex("F")
                .age(39)
                .background("He finished his PHD studies")
                .direction(Geographic)
                .portrait(getResourceAsByteArray("avatar/zereni.png"))//package relative path
                .user(alice)
                .build();

        teacherService.create(Bob);
        teacherService.create(Jakub);
        teacherService.create(Banhoff);
        teacherService.create(Sebastian);
    }

    /**
     * @param name name of the desired resource
     * @return array of bytes read from the resource
     */
    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            return is.readAllBytes();
        }
    }

}
