package pl.edu.pg.eti.hang.University.teacher.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Research implements Serializable {

    /**
     * Name of the research.
     */
    private String name;

    /**
     * Flavour text description for users.
     */
    private String description;

//    /**
//     * List of effects associated to this skill. This is not a list of objects because those are not stored in the
//     * database but predefined in the application.
//     */
//    private List<Class<? extends Research>> effects;

}
