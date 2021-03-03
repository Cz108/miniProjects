package pl.edu.pg.eti.hang.University.teacher.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.hang.University.people.entity.People;
import pl.edu.pg.eti.hang.University.user.entity.User;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entity for Teachers owned by the user. Represents characters basic stats (see {@link People}) as well as
 * direction and researches. Also contains link to user (see @link {@link User}) for the sake of database relationship.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "teachers")
public class Teacher extends People {

    /**
     * Teacher's background story.
     */
    private String background;

    /**s
     * Teacher's age.
     */
    private int age;

    /**
     * Teacher's direction (class).
     */
    @ManyToOne
    @JoinColumn(name ="direction")
    private Direction direction;

    /**
     * Owner of this teacher.
     */
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;
    /**
     * People's portrait. Images in database are stored as blobs (binary large objects).
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @ToString.Exclude
    private byte[] portrait;

}
