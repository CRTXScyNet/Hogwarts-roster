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
import ru.hogwarts.school.services.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerTest {
    @SpyBean
    private StudentService service;
    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private StudentController controller;

    @Test
    void addStudent() throws Exception {
        Student student = new Student(0L, "newStudent", 15);
        JSONObject object = new JSONObject();
        object.put("id", student.getId());
        object.put("name", student.getName());
        object.put("age", student.getAge());

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/students")
                        .content(object.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()));
    }

    @Test
    void getStudent() throws Exception {
        Student student = new Student();

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/students/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getFaculty() throws Exception {
        Faculty faculty = new Faculty(1L, "PTU", "gray");


        when(studentRepository.findFaculty_IdById(5L)).thenReturn(1L);
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/students/5/faculty")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getStudentByAge() throws Exception {
        List<Student> students = new ArrayList<>();
        students.add(new Student());
        students.add(new Student());
        students.add(new Student());

        when(studentRepository.findByAge(21)).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/students/age?age=21")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getAllStudents() throws Exception {
        List<Student> students = new ArrayList<>();
        students.add(new Student());
        students.add(new Student());
        students.add(new Student());

        when(studentRepository.findAll()).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/students/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getStudentByAgeBetween() throws Exception {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1L, "sofi", 15));
        students.add(new Student(2L, "safi", 18));
        students.add(new Student(3L, "sufi", 12));

        when(studentRepository.findByAgeBetween(10, 23)).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/students?min=10&max=23")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updateStudent() throws Exception {
        Student student = new Student(1L, "sofu", 15);
        JSONObject object = new JSONObject();
        object.put("id", student.getId());
        object.put("name", student.getName());
        object.put("age", student.getAge());

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/students")
                        .content(object.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()));
    }

    @Test
    void deleteStudent() throws Exception {
        Student student = new Student();
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/students/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}