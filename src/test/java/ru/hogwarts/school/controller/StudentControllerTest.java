package ru.hogwarts.school.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    @LocalServerPort
    private Long port;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUri;

    @BeforeEach
    public void init() {
        baseUri = "http://localhost:" + port + "/students";
        System.out.println(baseUri);
    }

    @Test
    public void contextLoad() {
        assertThat(studentRepository).isNotNull();
    }

    @Test
    void welcome() {
        assertThat(this.restTemplate.getForObject(baseUri + "/entry", String.class))
                .isEqualTo("Welcome to StudentController");
    }

    @Test
    void addStudent() throws Exception {
        Student student = new Student();
        student.setName("Stu");
        student.setAge(100);

        ResponseEntity<Student> responseEntity = this.restTemplate.postForEntity(baseUri, student, Student.class);

        assertThat(responseEntity.getBody().getAge()).isEqualTo(student.getAge());
        assertThat(responseEntity.getBody().getName()).isEqualTo(student.getName());

        studentRepository.deleteById(responseEntity.getBody().getId());
    }

    @Test
    void getStudent() {
        assertThat(this.restTemplate.getForObject(baseUri + "/1", String.class)).isNotNull();
    }

    @Test
    void getFaculty() {
        assertThat(this.restTemplate.getForObject(baseUri + "/1/faculty", String.class)).isNotNull();
    }

    @Test
    void getStudentByAge() {
        assertThat(this.restTemplate.getForObject(baseUri + "/age?age=13", String.class)).isNotNull();
    }

    @Test
    void getAllStudents() {
        assertThat(this.restTemplate.getForObject(baseUri + "/all", String.class)).isNotNull();
    }

    @Test
    void getStudentByAgeBetween() {
        assertThat(this.restTemplate.getForObject(baseUri + "/age?min=10&max=25", String.class)).isNotNull();
    }

    @Test
    void updateStudent() {
        Student student = new Student();
        student.setName("Stu");
        student.setAge(100);

        ResponseEntity<Student> responseEntity = this.restTemplate.postForEntity(baseUri, student, Student.class);

        Long id = responseEntity.getBody().getId();
        Student newStudent = new Student(id, "sos", 50);

        this.restTemplate.put(baseUri, newStudent, Student.class);

        Student updatedStudent = studentRepository.findById(id).get();

        assertThat(updatedStudent.getName()).isEqualTo(newStudent.getName());
        assertThat(updatedStudent.getAge()).isEqualTo(newStudent.getAge());

        studentRepository.deleteById(responseEntity.getBody().getId());
    }

    @Test
    void deleteStudent() {
        Student student = new Student();
        student.setName("Stu");
        student.setAge(100);

        ResponseEntity<Student> responseEntity = this.restTemplate.postForEntity(baseUri, student, Student.class);

        Long id = responseEntity.getBody().getId();
        Student newStudent = new Student(id, "sos", 50);

        this.restTemplate.put(baseUri, newStudent, Student.class);

        Student updatedStudent = studentRepository.findById(id).get();

        assertThat(updatedStudent.getName()).isEqualTo(newStudent.getName());
        assertThat(updatedStudent.getAge()).isEqualTo(newStudent.getAge());

        studentRepository.deleteById(responseEntity.getBody().getId());
        assertThat(this.restTemplate.getForObject(baseUri + "/" + id, String.class)).isNull();
    }

    @Test
    void uploadAvatar() throws Exception{

    }

    @Test
    void downloadAvatar() {

    }

    @Test
    void downloadAvatarFromDB() {

    }
}