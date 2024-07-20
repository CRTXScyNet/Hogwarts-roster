package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;

@Service
public class StudentService {
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public StudentService(StudentRepository repository, FacultyRepository facultyRepository) {
        this.studentRepository = repository;
        this.facultyRepository = facultyRepository;
    }

    public Student addStudent(Student student) {
        logger.info("Был вызван метод 'addStudent'");
        return studentRepository.save(student);
    }

    public Student getStudent(Long id) {
        logger.info("Был вызван метод 'getStudent'");
        return studentRepository.findById(id).orElse(null);
    }

    public Student updateStudent(Student student) {
        logger.info("Был вызван метод 'updateStudent'");
        return studentRepository.save(student);
    }

    public Student deleteStudent(Long id) {
        logger.info("Был вызван метод 'deleteStudent'");
        Student student = studentRepository.findById(id).orElse(null);
        if (student == null) {
            return null;
        }
        studentRepository.deleteById(id);
        return student;
    }

    public Collection<Student> getStudentByAge(int age) {
        logger.info("Был вызван метод 'getStudentByAge'");
        return studentRepository.findByAge(age);
    }

    public Collection<Student> getAllStudents() {
        logger.info("Был вызван метод 'getAllStudents'");
        return studentRepository.findAll();
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        logger.info("Был вызван метод 'findByAgeBetween'");
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getFaculty(Long id) {
        logger.info("Был вызван метод 'getFaculty'");
        id = studentRepository.findFaculty_IdById(id);
        if (id == null) {
            return null;
        }
        return facultyRepository.findById(id).orElse(null);
    }

    public Integer getAmountOfStudents() {
        logger.info("Был вызван метод 'getAmountOfStudents'");
        return studentRepository.getAmountOfStudents();
    }

    public Integer getAverageAgeOfStudents() {
        logger.info("Был вызван метод 'getAverageAgeOfStudents'");
        return studentRepository.getAverageAgeOfStudents();
    }

    public Collection<Student> getLastFiveStudents() {
        logger.info("Был вызван метод 'getLastFiveStudents'");
        return studentRepository.getLastFiveStudents();
    }
}
