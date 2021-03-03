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

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("api/teachers")
public class TeacherController {

    private TeacherService teacherService;
    private DirectionService directionService;

    @Autowired
    public TeacherController(TeacherService teacherService, DirectionService directionService) {
        this.teacherService = teacherService;
        this.directionService = directionService;
    }

    /**
     * @return list of teachers which will be converted to JSON
     */
    @GetMapping
    public ResponseEntity<GetTeachersResponse> getTeachers() {
        return ResponseEntity.ok(GetTeachersResponse.entityToDtoMapper().apply(teacherService.findAll()));
    }

    /**
     * @param id id of the teacher
     * @return single teacher in JSON format or 404 when teacher does not exist
     */
    @GetMapping("{id}")
    public ResponseEntity<GetTeacherResponse> getTeacher(@PathVariable("id") long id) {
//        Optional<Teacher> teacher = teacherService.find(id);
//        return teacher.map(value -> ResponseEntity.ok(GetTeacherResponse.entityToDtoMapper().apply(value)))
//                .orElseGet(() -> ResponseEntity.notFound().build());
        return  teacherService.find(id)
                .map(value -> ResponseEntity.ok(GetTeacherResponse.entityToDtoMapper().apply(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * @param request new teacher parsed from JSON
     * @param builder URI builder
     * @return response with location header
     */
    @PostMapping
    public ResponseEntity<Void> createTeacher(@RequestBody CreateTeacherRequest request, UriComponentsBuilder builder) {
        Teacher teacher = CreateTeacherRequest
                .dtoToEntityMapper(name -> directionService.find(name).orElseThrow(), () -> null)
                .apply(request);
        teacher = teacherService.create(teacher);
        return ResponseEntity.created(builder.pathSegment("api", "teachers", "{id}").
                buildAndExpand(teacher.getId()).toUri()).build();
    }

    /**
     * Deletes selected teacher.
     *
     * @param id teacher's id
     * @return accepted for not found if teacher does not exist
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable("id") long id) {
        Optional<Teacher> teacher = teacherService.find(id);
        if (teacher.isPresent()) {
            teacherService.delete(teacher.get().getId());
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Updates existing teacher.
     *
     * @param request teacher's data parsed from JSON
     * @param id      teacher's id
     * @return accepted or not found if teacher does not exist
     */
    @PutMapping("{id}")
    public ResponseEntity<Void> updateTeacher(@RequestBody UpdateTeacherRequest request, @PathVariable("id") long id) {
        Optional<Teacher> teacher = teacherService.find(id);
        if (teacher.isPresent()) {
            UpdateTeacherRequest.dtoToEntityUpdater().apply(teacher.get(), request);
            teacherService.update(teacher.get());
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * @param id teacher's id
     * @return teacher's portrait or not found if teacher does not exist
     */
    @GetMapping(value = "{id}/portrait", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getTeacherPortrait(@PathVariable("id") long id) {
        Optional<Teacher> teacher = teacherService.find(id);
        return teacher.map(value -> ResponseEntity.ok(value.getPortrait()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Updates teachers portrait.
     *
     * @param id       teacher's id
     * @param portrait named multipart form part (parameter)
     * @return accepted or not found if teacher does not exist
     * @throws IOException on any I/O error
     */
    @PutMapping(value = "{id}/portrait", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateTeacherPortrait(@PathVariable("id") long id,
                                                        @RequestParam("portrait") MultipartFile portrait) throws IOException {
        Optional<Teacher> teacher = teacherService.find(id);
        if (teacher.isPresent()) {
            teacherService.updatePortrait(teacher.get().getId(), portrait.getInputStream());
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}
