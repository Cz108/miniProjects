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

import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateTeacherRequest {

    private String name;

    private String background;

    private int age;

    public static BiFunction<Teacher, UpdateTeacherRequest, Teacher> dtoToEntityUpdater() {
        return (teacher, request) -> {
            teacher.setName(request.getName());
            teacher.setAge(request.getAge());
            teacher.setBackground(request.getBackground());
            return teacher;
        };
    }


}
