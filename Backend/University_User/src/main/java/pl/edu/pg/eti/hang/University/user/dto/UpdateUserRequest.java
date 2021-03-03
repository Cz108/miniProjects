package pl.edu.pg.eti.hang.University.user.dto;

import lombok.*;
import pl.edu.pg.eti.hang.University.user.entity.User;

import java.time.LocalDate;
import java.util.function.BiFunction;

/**
 * PUT user request. Contains only fields which can be changed byt the user while updating its profile. User is defined
 * in {@link pl.edu.pg.eti.hang.Universiy.user.entity.User}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateUserRequest {

    /**
     * User's name.
     */
    private String name;

    /**
     * User's surname.
     */
    private String surname;

    /**
     * User's birth day.
     */
    private LocalDate birthDate;

    /**
     * User's email.
     */
    private String email;

    public static BiFunction<User, UpdateUserRequest, User> dtoToEntityUpdater() {
        return (user, request) -> {
            user.setName(request.getName());
            user.setSurname(request.getSurname());
            user.setBirthDate(request.getBirthDate());
            user.setEmail(request.getEmail());
            return user;
        };
    }

}
