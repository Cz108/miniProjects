package pl.edu.pg.eti.hang.University.teacher.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.hang.University.teacher.entity.Direction;
import pl.edu.pg.eti.hang.University.teacher.repository.DirectionRepository;

import java.util.Optional;


/**
 * Service layer for all business actions regarding teacher's direction entity.
 */
@Service
public class DirectionService {

    /**
     * Repository for direction entity.
     */
    private DirectionRepository repository;

    /**
     * @param repository repository for direction entity
     */
    @Autowired
    public DirectionService(DirectionRepository repository) {
        this.repository = repository;
    }

    /**
     * @param name name of the direction
     * @return container with direction entity
     */
    public Optional<Direction> find(String name) {
        return repository.findById(name);
    }

    /**
     * Stores new direction in the data store.
     *
     * @param direction new direction to be saved
     */
    @Transactional
    public void create(Direction direction) {
        repository.save(direction);
    }

}
