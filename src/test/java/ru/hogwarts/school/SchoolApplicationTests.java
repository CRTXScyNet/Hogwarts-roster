package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SchoolApplicationTests {
    @LocalServerPort
    private long port;
    @Autowired
    private FacultyController facultyController;
    @Autowired
    private StudentController studentController;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception{
        assertThat(facultyController).isNotNull();
        assertThat(studentController).isNotNull();
    }

    @Test
    public void testEntryPhrase(){
        assertThat(this.restTemplate.getForObject("http://localhost:"+port+"/students/entry",String.class))
                .isEqualTo("Welcome to StudentController");
    }
}
