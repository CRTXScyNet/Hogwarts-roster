package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public StudentService(StudentRepository repository, FacultyRepository facultyRepository) {
        this.studentRepository = repository;
        this.facultyRepository = facultyRepository;
    }

//    @PostConstruct
//    private void init() {
//        addStudent(new Student(1L,"Harry Potter", 13));
//        addStudent(new Student(2L, "Hermione Granger", 13));
//        addStudent(new Student(3L, "Ronald Weasley", 13));
//    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudent(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student deleteStudent(Long id) {
        Student student = studentRepository.findById(id).orElse(null);
        if (student == null){
            return null;
        }
        studentRepository.deleteById(id);
        return student;
    }

    public Collection<Student> getStudentByAge(int age) {
        return studentRepository.findByAge(age);
    }

    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getFaculty(Long id) {
        id = studentRepository.findFaculty_IdById(id);
        if (id == null) {
            return null;
        }
        return facultyRepository.findById(id).orElse(null);
    }

    public Integer getAmountOfStudents() {
        return studentRepository.getAmountOfStudents();
    }
    public Integer getAverageAgeOfStudents() {
        return studentRepository.getAverageAgeOfStudents();
    }

    public Collection<Student> getLastFiveStudents() {
        return studentRepository.getLastFiveStudents();
    }
}
