package pl.edu.pg.eti.hang.University.user.event.dto;

import lombok.*;
import pl.edu.pg.eti.hang.University.user.entity.User;

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
     * @return mapper for convenient converting dto object to entity object
     */
    public static Function<User, CreateUserRequest> entityToDtoMapper() {
        return entity -> CreateUserRequest.builder()
                .login(entity.getLogin())
                .build();
    }

}
