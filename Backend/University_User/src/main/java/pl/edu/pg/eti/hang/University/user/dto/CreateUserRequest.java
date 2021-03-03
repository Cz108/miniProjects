package pl.edu.pg.eti.hang.University.user.dto;

import lombok.*;
import pl.edu.pg.eti.hang.University.digest.Sha256Utility;
import pl.edu.pg.eti.hang.University.user.entity.User;

import java.time.LocalDate;
import java.util.function.Function;

/**
 * PSOT user request. Contains only fields that can be set during user creation. User is defined in
 * {@link pl.edu.pg.eti.hang.University.user.entity.User}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateUserRequest {

    /**
     * User's login.
     */
    private String login;

    /**
     * User's name.
     */
    private String name;

    /**
     * User's surname.
     */
    private String surname;

    /**
     * User's birth date.
     */
    private LocalDate birthDate;

    /**
     * User's password.
     */
    private String password;

    /**
     * User's email.
     */
    private String email;

    /**
     * @return mapper for convenient converting dto object to entity object
     */
    public static Function<CreateUserRequest, User> dtoToEntityMapper() {
        return request -> User.builder()
                .login(request.getLogin())
                .name(request.getName())
                .surname(request.getSurname())
                .birthDate(request.getBirthDate())
                .password(Sha256Utility.hash(request.getPassword()))
                .email(request.getEmail())
                .build();
    }

}
