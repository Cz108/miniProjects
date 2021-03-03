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
import pl.edu.pg.eti.hang.University.teacher.entity.Direction;
import pl.edu.pg.eti.hang.University.user.entity.User;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * POST character request. Contains only fields that can be set up byt the user while creating a new teacher
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateTeacherRequest {

    /**
     * Name of the teacher.
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
     * Name of the teacher's direction.
     */
    private String direction;


    public static Function<CreateTeacherRequest, Teacher> dtoToEntityMapper(
            Function<String, Direction> directionFunction,
            Supplier<User> userSupplier) {
        return request -> Teacher.builder()
                .name(request.getName())
                .age(request.getAge())
                .background(request.getBackground())
                .direction(directionFunction.apply(request.getDirection()))
                .user(userSupplier.get())
                .build();
    }

}

