package ru.hogwarts.school.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService service) {
        this.studentService = service;
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = studentService.getStudent(id);
        if (student == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        if (studentService.getStudent(student.getId()) == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(studentService.updateStudent(student));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        Student student = studentService.deleteStudent(id);
        if (student == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(student);
    }
    @GetMapping()
    public Collection<Student> getStudentByAge(@RequestParam int age){
       return studentService.getStudentByAge(age);
    }
}
