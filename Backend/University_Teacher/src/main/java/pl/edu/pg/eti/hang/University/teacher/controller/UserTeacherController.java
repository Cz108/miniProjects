package pl.edu.pg.eti.hang.University.teacher.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;


import pl.edu.pg.eti.hang.University.teacher.dto.CreateTeacherRequest;
import pl.edu.pg.eti.hang.University.teacher.dto.GetTeacherResponse;
import pl.edu.pg.eti.hang.University.teacher.dto.GetTeachersResponse;
import pl.edu.pg.eti.hang.University.teacher.dto.UpdateTeacherRequest;

import pl.edu.pg.eti.hang.University.teacher.entity.Teacher;
import pl.edu.pg.eti.hang.University.teacher.service.TeacherService;
import pl.edu.pg.eti.hang.University.teacher.service.DirectionService;

import pl.edu.pg.eti.hang.University.user.entity.User;
import pl.edu.pg.eti.hang.University.user.service.UserService;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("api/users/{username}/teachers")
public class UserTeacherController {


    private TeacherService teacherService;
    private DirectionService directionService;
    private UserService userService;

    @Autowired
    public UserTeacherController(TeacherService teacherService, DirectionService directionService, UserService userService) {
        this.teacherService = teacherService;
        this.directionService = directionService;
        this.userService = userService;
    }
    /**
     * @param username existing user's username (login)
     * @return list of users' teachers which will be converted to JSON or not found if user does not exist
     */
    @GetMapping
    public ResponseEntity<GetTeachersResponse> getTeachers(@PathVariable("username") String username) {
        Optional<User> user = userService.find(username);
        return user.map(value -> ResponseEntity.ok(GetTeachersResponse.entityToDtoMapper().apply(teacherService.findAll(value))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("{id}")
    public ResponseEntity<GetTeacherResponse> getTeacher(@PathVariable("username") String username,
                                                             @PathVariable("id") long id) {
//        Optional<User> user = userService.find(username);
//        if (user.isPresent()) {
//            Optional<Teacher> teacher = teacherService.find(user.get(), id);
//            return teacher.map(value -> ResponseEntity.ok(GetTeacherResponse.entityToDtoMapper().apply(value)))
//                    .orElseGet(() -> ResponseEntity.notFound().build());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
        return teacherService.find(username, id)
                .map(value -> ResponseEntity.ok(GetTeacherResponse.entityToDtoMapper().apply(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> createTeacher(@PathVariable("username") String username,
                                                @RequestBody CreateTeacherRequest request,
                                                UriComponentsBuilder builder) {
        Optional<User> user = userService.find(username);
        if (user.isPresent()) {
            Teacher teacher = CreateTeacherRequest
                    .dtoToEntityMapper(name -> directionService.find(name).orElseThrow(), user::get)
                    .apply(request);
            teacher = teacherService.create(teacher);
            return ResponseEntity.created(builder.pathSegment("api", "users", "{username}", "teachers", "{id}")
                    .buildAndExpand(user.get().getLogin(), teacher.getId()).toUri()).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable("username") String username,
                                                @PathVariable("id") long id) {
        Optional<Teacher> teacher = teacherService.find(username, id);
        if (teacher.isPresent()) {
            teacherService.delete(teacher.get().getId());
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     *     Updates existing teacher.
     */

    @PutMapping("{id}")
    public ResponseEntity<Void> updateTeacher(@PathVariable("username") String username,
                                                @RequestBody UpdateTeacherRequest request,
                                                @PathVariable("id") long id) {
        Optional<Teacher> teacher = teacherService.find(username, id);
        if (teacher.isPresent()) {
            UpdateTeacherRequest.dtoToEntityUpdater().apply(teacher.get(), request);
            teacherService.update(teacher.get());
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "{id}/portrait", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getTeacherPortrait(@PathVariable("username") String username,
                                                       @PathVariable("id") long id) {
        return teacherService.find(username, id)
                .map(value -> ResponseEntity.ok(value.getPortrait()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(value = "{id}/portrait", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateTeacherPortrait(@PathVariable("username") String username,
                                                        @PathVariable("id") long id,
                                                        @RequestParam("portrait") MultipartFile portrait) throws IOException {
            Optional<Teacher> teacher = teacherService.find(username, id);
            if (teacher.isPresent()) {
                teacherService.updatePortrait(teacher.get().getId(), portrait.getInputStream());
                return ResponseEntity.accepted().build();
            } else {
                return ResponseEntity.notFound().build();
            }
    }

}
