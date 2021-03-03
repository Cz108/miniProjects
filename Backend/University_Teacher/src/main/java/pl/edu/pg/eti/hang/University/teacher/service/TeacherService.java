package pl.edu.pg.eti.hang.University.teacher.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.hang.University.teacher.entity.Teacher;
import pl.edu.pg.eti.hang.University.teacher.repository.TeacherRepository;
import pl.edu.pg.eti.hang.University.user.entity.User;
import pl.edu.pg.eti.hang.University.user.repository.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for all business actions regarding teacher entity.
 */
@Service
public class TeacherService {

    /**
     * Repository for teacher entity.
     */
    private TeacherRepository teacherRepository;
    private UserRepository userRepository;

    /**
     * @param repository repository for teacher entity
     */
    @Autowired
    public TeacherService(TeacherRepository teacherRepository, UserRepository userRepository) {
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
    }

    /**
     * Finds single teacher.
     *
     * @param id teacher's id
     * @return container with teacher
     */
    public Optional<Teacher> find(Long id) {
        return teacherRepository.findById(id);
    }

    /**
     * @param id   teacher's id
     * @param user existing user
     * @return selected teacher for user
     */
    public Optional<Teacher> find(User user, Long id) {
        return teacherRepository.findByIdAndUser(id, user);
    }

    /**
     * @param username user's login
     * @param id       teacher's id
     * @return selected teacher for user if present
     */
    public Optional<Teacher> find(String username, Long id) {
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            return teacherRepository.findByIdAndUser(id, user.get());
        } else {
            return Optional.empty();
        }
    }
    /**
     * @return all available teachers
     */

    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    /**
     * @param user existing user, teacher's owner
     * @return all available teachers of the selected user
     */
    public List<Teacher> findAll(User user) {
        return teacherRepository.findAllByUser(user);
    }

    /**
     * Creates new teacher.
     *
     * @param teacher new teacher
     */
    @Transactional
    public Teacher create(Teacher teacher) { return teacherRepository.save(teacher);
    }

    /**
     * Updates existing teacher.
     *
     * @param teacher teacher to be updated
     */
    @Transactional
    public void update(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    /**
     * Deletes existing teacher.
     *
     * @param teacher existing teacher's id to be deleted
     */
    @Transactional
    public void delete(Long teacher) {
        teacherRepository.deleteById(teacher);
    }

    /**
     * Updates portrait of the teacher.
     *
     * @param id teacher's id
     * @param is input stream containing new portrait
     */
    @Transactional
    public void updatePortrait(Long id, InputStream is) {
        teacherRepository.findById(id).ifPresent(teacher -> {
            try {
                teacher.setPortrait(is.readAllBytes());
 //               repository.update(teacher);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }

}