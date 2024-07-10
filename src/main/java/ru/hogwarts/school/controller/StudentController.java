package ru.hogwarts.school.controller;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    private final AvatarService avatarService;

    public StudentController(StudentService service, AvatarService avatarService) {
        this.studentService = service;
        this.avatarService = avatarService;
    }

    @GetMapping("/entry")
    public String welcome() {
        return "Welcome to " + this.getClass().getSimpleName();
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = studentService.getStudent(id);
        if (student == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping("/{id}/faculty")
    public ResponseEntity<Faculty> getFaculty(@PathVariable long id) {
        Faculty faculty = studentService.getFaculty(id);
        if (faculty == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("/age")
    public Collection<Student> getStudentByAge(@RequestParam int age) {
        return studentService.getStudentByAge(age);
    }

    @GetMapping("/all")
    public Collection<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping()
    public Collection<Student> getStudentByAgeBetween(@RequestParam int min, @RequestParam int max) {
        return studentService.findByAgeBetween(min, max);
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.updateStudent(student));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        Student student = studentService.deleteStudent(id);
        if (student == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(student);
    }


    @PostMapping(value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long id, @RequestParam MultipartFile avatar) throws IOException {
        if (avatar.getSize() >= 1024 * 300) {
            ResponseEntity.badRequest().body("File is too big");
        }
        avatarService.uploadAvatar(id, avatar);

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}/avatar")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.findAvatar(id);
        System.out.println(avatar.getFilePath());
        System.out.println(avatar.getFileSize());
        System.out.println(avatar.getData().length);
        System.out.println(avatar.getMediaType());
        Path path = Path.of(avatar.getFilePath());
        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream()) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }

    @GetMapping(value = "/{id}/avatar/preview")
    public ResponseEntity<byte[]> downloadAvatarFromDB(@PathVariable Long id) {
        Avatar avatar = avatarService.findAvatar(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }
}
