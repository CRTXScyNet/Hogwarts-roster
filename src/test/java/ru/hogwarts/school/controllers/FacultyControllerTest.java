package ru.hogwarts.school.controllers;

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
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.services.FacultyService;

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
                        .get("/faculties/1"))
                .andDo(print())
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void getStudents() throws Exception {
        List<Student> students = new ArrayList<>();
        students.add(new Student(0L, "sps", 12));
        students.add(new Student(0L, "ssp", 13));
        students.add(new Student(0L, "pss", 14));

        when(studentRepository.findByFacultyId(anyLong())).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculties/1/students")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].age").value(12))
                .andExpect(jsonPath("$[1].age").value(13))
                .andExpect(jsonPath("$[2].age").value(14));
    }

    @Test
    void getFacultyByColor() throws Exception {
        List<Faculty> faculty = new ArrayList<>();
        faculty.add(new Faculty(0L, "sps", "sd"));
        faculty.add(new Faculty(0L, "ssp", "sd"));
        faculty.add(new Faculty(0L, "pss", "sd"));

        when(facultyRepository.findByColor(any(String.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculties/color?color=sd")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].color").value("sd"))
                .andExpect(jsonPath("$[1].color").value("sd"))
                .andExpect(jsonPath("$[2].color").value("sd"));

    }

    @Test
    void getAllFaculty() throws Exception {
        List<Faculty> faculty = new ArrayList<>();
        faculty.add(new Faculty(0L, "sps", "sd"));
        faculty.add(new Faculty(0L, "ssp", "sd"));
        faculty.add(new Faculty(0L, "pss", "sd"));

        when(facultyRepository.findAll()).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculties/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].color").value("sd"))
                .andExpect(jsonPath("$[1].color").value("sd"))
                .andExpect(jsonPath("$[2].color").value("sd"));
    }

    @Test
    void findByNameOrColor() throws Exception {

        Faculty faculty = new Faculty(0L, "sps", "sd");


        when(facultyRepository.findByColorIgnoreCase(any(String.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculties?part=sd")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("sps"))
                .andExpect(jsonPath("$.color").value("sd"));

        when(facultyRepository.findByColorIgnoreCase(any(String.class))).thenReturn(null);
        when(facultyRepository.findByNameIgnoreCase(any(String.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculties?part=sd")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("sps"))
                .andExpect(jsonPath("$.color").value("sd"));

    }

    @Test
    void updateFaculty() throws Exception{
        Faculty faculty = new Faculty(0L, "sps", "sd");

        JSONObject object = new JSONObject();
        object.put("id",faculty.getId());
        object.put("name",faculty.getName());
        object.put("color",faculty.getColor());

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculties?part=sd")
                        .content(object.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("sps"))
                .andExpect(jsonPath("$.color").value("sd"));
    }

    @Test
    void deleteFaculty() throws Exception{
        Faculty faculty = new Faculty();

        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(faculty));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculties/1"))
                .andExpect(status().isOk());
    }
}