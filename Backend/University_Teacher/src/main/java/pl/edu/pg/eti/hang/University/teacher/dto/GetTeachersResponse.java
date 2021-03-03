package pl.edu.pg.eti.hang.University.teacher.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetTeachersResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Teacher {

        private Long id;

        private String name;

    }
    @Singular
    private List<Teacher> teachers;

    public static Function<Collection<pl.edu.pg.eti.hang.University.teacher.entity.Teacher>, GetTeachersResponse> entityToDtoMapper() {
        return teachers -> {
            GetTeachersResponseBuilder response = GetTeachersResponse.builder();
            teachers.stream()
                    .map(teacher -> Teacher.builder()
                            .id(teacher.getId())
                            .name(teacher.getName())
                            .build())
                    .forEach(response::teacher);
            return response.build();
        };
    }

}
