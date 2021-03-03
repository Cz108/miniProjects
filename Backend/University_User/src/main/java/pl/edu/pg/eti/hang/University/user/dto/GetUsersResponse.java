package pl.edu.pg.eti.hang.University.user.dto;

import lombok.*;
import pl.edu.pg.eti.hang.University.user.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * GET users response. Contains user names of users in the system. User name ios the same as login.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetUsersResponse {

    /**
     * List of all user names.
     */
    @Singular
    private List<String> users;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Collection<User>, GetUsersResponse> entityToDtoMapper() {
        return characters -> {
            GetUsersResponseBuilder response = GetUsersResponse.builder();
            characters.stream()
                    .map(User::getLogin)
                    .forEach(response::user);
            return response.build();
        };
    }
}
