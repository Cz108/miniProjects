package pl.edu.pg.eti.hang.University.user.entity;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.hang.University.teacher.entity.Teacher;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;




/**
 * Entity for system user(stduent). Represents information about particular user as well as credentials for authorization and
 * authentication needs.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "users")
public class User implements Serializable {

    /**
     * User's login.
     */
    @Id
    private String login;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.REMOVE
//            orphanRemoval = true
    )
    @ToString.Exclude
    private List<Teacher> teachers;

}
