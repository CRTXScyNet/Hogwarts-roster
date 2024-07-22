package ru.hogwarts.school.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.services.StudentService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService service) {
        this.studentService = service;
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = studentService.getStudent(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/{id}/faculty")
    public ResponseEntity<Faculty> getFaculty(@PathVariable long id) {
        Faculty faculty = studentService.getFaculty(id);
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

    @GetMapping("/all/sorted")
    public Collection<String> getSortedStudentsNames() {
        return studentService.getSortedStudentsNames();
    }

    @GetMapping()
    public Collection<Student> getStudentByAgeBetween(@RequestParam int min, @RequestParam int max) {
        return studentService.findByAgeBetween(min, max);
    }

    @GetMapping("/all/amount")
    public Integer getAmountOfStudents() {
        return studentService.getAmountOfStudents();
    }

    @GetMapping("/average-age")
    public Integer getAverageAgeOfStudents() {
        return studentService.getAverageAgeOfStudents();

    }

    @GetMapping("/average-age/with-stream")
    public ResponseEntity<Double> getAverageAgeOfStudentsWithStream() {
        Double d = studentService.getAverageAgeOfStudentsWithStream();
        return ResponseEntity.ok(d);

    }

    @GetMapping("/get-last-five")
    public Collection<Student> getLastFiveStudents() {
        return studentService.getLastFiveStudents();
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.updateStudent(student));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        Student student = studentService.deleteStudent(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/print-parallel")
    public void printStudentsInParallel(){
        List<Student> studentList = new ArrayList<>(getAllStudents());
        System.out.println(studentList.get(0).getName());
        System.out.println(studentList.get(1).getName());
        new Thread(() -> {
            System.out.println(studentList.get(2).getName());
            System.out.println(studentList.get(3).getName());
        }).start();
        new Thread(() -> {
            System.out.println(studentList.get(4).getName());
            System.out.println(studentList.get(5).getName());
        }).start();
    }

private final Object studentSynchronizer = new Object();
    @GetMapping("/print-synchronized")
    public void printStudentsSynchronized(){
        List<Student> studentList = new ArrayList<>(getAllStudents());
        synchronizedMethod(studentList.get(0));
        synchronizedMethod(studentList.get(1));
        new Thread(() -> {
            synchronizedMethod(studentList.get(2));
            synchronizedMethod(studentList.get(3));
        }).start();
        new Thread(() -> {
            synchronizedMethod(studentList.get(4));
            synchronizedMethod(studentList.get(5));
        }).start();
    }
    public void synchronizedMethod(Student student){
        synchronized (studentSynchronizer){
            System.out.println(student.getName());
        }
    }
}
