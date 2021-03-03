package pl.edu.pg.eti.hang.University.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import pl.edu.pg.eti.hang.University.user.dto.CreateUserRequest;
import pl.edu.pg.eti.hang.University.user.entity.User;
import pl.edu.pg.eti.hang.University.user.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("api/users")
public class UserController {


    private UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    /**
     * Deletes selected user.
     *
     * @param username user's login
     * @return accepted for not found if character does not exist
     */
    @DeleteMapping("{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable("username") String username) {
        Optional<User> user = userService.find(username);
        if (user.isPresent()) {
            userService.delete(user.get());
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * @param request new user parsed from JSON
     * @param builder URI builder
     * @return response with location header
     */
    @PostMapping("")
    public ResponseEntity<Void> createUser(@RequestBody CreateUserRequest request, UriComponentsBuilder builder) {
        User user = CreateUserRequest.dtoToEntityMapper().apply(request);
        userService.create(user);
        return ResponseEntity.created(builder.pathSegment("api", "users", "{username}")
                .buildAndExpand(user.getLogin()).toUri()).build();
    }

}
