package ru.hogwarts.school.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.awt.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class FacultyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FacultyRepository repository;

    @SpyBean
    private FacultyService service;

    @InjectMocks
    private FacultyController controller;

//    @BeforeEach
//    void  init(){
//
//    }

    @Test
    void addFaculty() throws Exception{
        Faculty faculty = new Faculty();
        faculty.setName("Bro");
        faculty.setColor("rainbow");

        when(repository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                .post("faculty")
                .content(faculty.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(isNotNull()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));
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