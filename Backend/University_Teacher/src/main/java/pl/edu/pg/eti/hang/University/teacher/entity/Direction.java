package pl.edu.pg.eti.hang.University.teacher.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Entity class for teachers' direction (classes). Describes name of the direction and researches.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "directions")
public class Direction implements Serializable {

    /**
     * Name of the Direction.
     */
    @Id
    private String name;
    /**
     * Set of researches available.
     */
    @Transient
    private Map<Integer, Research> researches;

    @OneToMany
            (
            mappedBy = "direction",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    @ToString.Exclude
    private List<Teacher> teachers;

}