package pl.edu.pg.eti.hang.University.teacher.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pg.eti.hang.University.teacher.entity.Direction;



@Repository
public interface DirectionRepository extends JpaRepository<Direction, String> {

}