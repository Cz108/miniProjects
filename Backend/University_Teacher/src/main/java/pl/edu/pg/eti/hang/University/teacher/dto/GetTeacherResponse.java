package pl.edu.pg.eti.hang.University.teacher.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import pl.edu.pg.eti.hang.University.teacher.entity.Teacher;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode

public class GetTeacherResponse {

    /**
     * Unique id identifying character.
     */
    private Long id;

    /**
     * Name of the character.
     */
    private String name;

    /**
     * Character's background story.
     */
    private String background;

    /**
     * Character's age.
     */
    private int age;

    /**
     * Name of the characters's profession.
     */
    private String direction;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Teacher, GetTeacherResponse> entityToDtoMapper() {
        return teacher -> GetTeacherResponse.builder()
                .id(teacher.getId())
                .name(teacher.getName())
                .age(teacher.getAge())
                .background(teacher.getBackground())
                .direction(teacher.getDirection().getName())
                .build();
    }

}
