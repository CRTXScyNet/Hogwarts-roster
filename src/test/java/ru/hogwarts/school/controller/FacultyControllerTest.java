package ru.hogwarts.school.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerTest {
    @LocalServerPort
    private long port;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private TestRestTemplate restTemplate;
    private String baseUri;

    @BeforeEach
    public void init(){
        baseUri = "http://localhost:"+port+"/faculties";
        System.out.println(baseUri);
    }
    @Test
    void contextLoads() throws Exception{
        assertThat(facultyRepository).isNotNull();

    }
    @Test
    void welcome() throws Exception{
        assertThat(this.restTemplate.getForObject(baseUri+"/entry",String.class))
                .isEqualTo("Welcome to FacultyController");
    }

    @Test
    void addFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName("Ну и зачем");
        faculty.setColor("захламляем");
        assertThat(this.restTemplate.postForObject(baseUri, faculty, String.class)).isNotNull();
        facultyRepository.deleteById(facultyRepository.findByColorIgnoreCase("захламляем").getId());
    }

    @Test
    void getFaculty() {
        assertThat(this.restTemplate.getForObject(baseUri+"/1",String.class))
                .isNotNull();
    }

    @Test
    void getStudents() {
        assertThat(this.restTemplate.getForObject(baseUri+"/1/students",String.class))
                .isNotNull();
    }

    @Test
    void getFacultyByColor() {
        assertThat(this.restTemplate.getForObject(baseUri+"/color?color=pa",String.class))
                .isNotNull();
    }

    @Test
    void getAllFaculty() {
        assertThat(this.restTemplate.getForObject(baseUri+"/all",String.class))
                .isNotNull();
    }

    @Test
    void findByNameOrColor() {
        assertThat(this.restTemplate.getForObject(baseUri+"?part=yellow",String.class))
                .isNotNull();
    }

    @Test
    void updateFaculty() {
        Long id = 15L;
Faculty faculty = new Faculty(id,"Bar","golden");
        this.restTemplate.put(baseUri,faculty);
        facultyRepository.deleteById(facultyRepository.findByColorIgnoreCase("golden").getId());
    }

    @Test
    void deleteFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName("Ну и зачем");
        faculty.setColor("захламляем");
        this.restTemplate.postForObject(baseUri, faculty, String.class);

        Long id = facultyRepository.findByColorIgnoreCase("захламляем").getId();
        assertThat(this.restTemplate.getForObject(baseUri+"/"+id,String.class)).isNotNull();

        this.restTemplate.delete(baseUri+"/"+id);
        assertThat(this.restTemplate.getForObject(baseUri+"/"+id,String.class)).isNull();

    }
}