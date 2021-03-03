package pl.edu.pg.eti.hang.University.user.event.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import pl.edu.pg.eti.hang.University.user.entity.User;
import pl.edu.pg.eti.hang.University.user.event.dto.CreateUserRequest;

@Repository
public class UserEventRepository  {

    private RestTemplate restTemplate;

    @Autowired
    public UserEventRepository(@Value("${University.teachers.url}") String baseUrl) {
        restTemplate = new RestTemplateBuilder().rootUri(baseUrl).build();
    }

    public void delete(User user) {
        restTemplate.delete("/users/{username}", user.getLogin());
    }

    public void create(User user) {
        restTemplate.postForLocation("/users", CreateUserRequest.entityToDtoMapper().apply(user));
    }
}
