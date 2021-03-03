package pl.edu.pg.eti.hang.University.teacher.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.edu.pg.eti.hang.University.teacher.entity.Teacher;
import pl.edu.pg.eti.hang.University.user.entity.User;

import java.util.List;
import java.util.Optional;



@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    /**
     * Seeks for single user's character.
     *
     * @param id   character's id
     * @param user characters's owner
     * @return container (can be empty) with character
     */
    Optional<Teacher> findByIdAndUser(Long id, User user);

    /**
     * Seeks for all user's characters.
     *
     * @param user characters' owner
     * @return list (can be empty) of user's characters
     */
    List<Teacher> findAllByUser(User user);

}
