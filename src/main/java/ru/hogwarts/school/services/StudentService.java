package ru.hogwarts.school.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.FacultyIsNotFoundException;
import ru.hogwarts.school.exceptions.StudentIsNotFoundException;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
        return studentRepository.findById(id).orElseThrow(StudentIsNotFoundException::new);
    }

    public Student updateStudent(Student student) {
        logger.info("Был вызван метод 'updateStudent'");
        return studentRepository.save(student);
    }

    public Student deleteStudent(Long id) {
        logger.info("Был вызван метод 'deleteStudent'");
        Student student = studentRepository.findById(id).orElseThrow(StudentIsNotFoundException::new);
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
        Optional<Long> id2 = Optional.of(studentRepository.findFaculty_IdById(id));

        return facultyRepository.findById(id2.orElseThrow(FacultyIsNotFoundException::new))
                .orElseThrow(FacultyIsNotFoundException::new);
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

    public List<String> getSortedStudentsNames(){
        logger.info("Был вызван метод 'getSortedStudentsNames'");
        return studentRepository.findAll().stream()
                .map(student -> student.getName().toUpperCase())
                .filter(name -> name.startsWith("A"))
                .sorted().toList();
    }
    public Double getAverageAgeOfStudentsWithStream(){
        logger.info("Был вызван метод 'getAverageAgeOfStudents'");
        double aDouble =  studentRepository.findAll()
                .stream()
                .mapToInt(Student::getAge)
                .average()
                .orElseThrow(StudentIsNotFoundException::new);
        return Math.floor(aDouble);
    }
}
