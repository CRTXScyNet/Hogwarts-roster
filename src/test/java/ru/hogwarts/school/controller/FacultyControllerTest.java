package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import static org.mockito.Mockito.*;

@WebMvcTest
class FacultyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FacultyRepository repository;

    @Spy
    private FacultyService service;

    @InjectMocks
    private FacultyController controller;

    @Test
    void addFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName("Bro");
        faculty.setColor("rainbow");

        when(repository.save(any(Faculty.class))).thenReturn(faculty);

    }

    @Test
    void getFaculty() {
    }

    @Test
    void getStudents() {
    }

    @Test
    void getFacultyByColor() {
    }

    @Test
    void getAllFaculty() {
    }

    @Test
    void findByNameOrColor() {
    }

    @Test
    void updateFaculty() {
    }

    @Test
    void deleteFaculty() {
    }
}