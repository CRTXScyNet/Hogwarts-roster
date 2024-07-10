package ru.hogwarts.school.controller;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
class FacultyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FacultyRepository facultyRepository;
    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private FacultyService service;

    @InjectMocks
    private FacultyController controller;


    @Test
    void addFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Bro");
        faculty.setColor("rainbow");
        JSONObject object = new JSONObject();
        object.put("id", 1514L);
        object.put("name", faculty.getName());
        object.put("color", faculty.getColor());
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculties")
                        .content(object.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));
    }

    @Test
    void getFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Bro");
        faculty.setColor("rainbow");
        faculty.setId(1L);
        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculties/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void getStudents() throws Exception {
        List<Student> students = new ArrayList<>();
        students.add(new Student(0L, "sos", 12));
        students.add(new Student(0L, "sos", 13));
        students.add(new Student(0L, "sos", 14));

        when(studentRepository.findByFacultyId(anyLong())).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculties/1/students")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$[0].age").value(12))
                .andExpect(jsonPath("$[1].age").value(13))
                .andExpect(jsonPath("$[2].age").value(14));
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