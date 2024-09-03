package ru.hogwarts.school.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.FacultyIsNotFoundException;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.Comparator;

@Service
public class FacultyService {
    private final Logger logger = LoggerFactory.getLogger(FacultyService.class);
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    public FacultyService(FacultyRepository repository, StudentRepository studentRepository) {
        this.facultyRepository = repository;
        this.studentRepository = studentRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        logger.info("Method 'addFaculty' was called");
        return facultyRepository.save(faculty);
    }


    public Faculty getFaculty(Long id) {
        logger.info("Method 'getFaculty' was called");
        return facultyRepository.findById(id).orElseThrow(FacultyIsNotFoundException::new);
    }

    public Faculty updateFaculty(Faculty faculty) {
        logger.info("Method 'updateFaculty' was called");
        return facultyRepository.save(faculty);
    }

    public Faculty deleteFaculty(Long id) {
        logger.info("Method 'deleteFaculty' was called");
        Faculty faculty = facultyRepository.findById(id).orElseThrow(FacultyIsNotFoundException::new);
        facultyRepository.deleteById(id);
        return faculty;
    }

    public Collection<Faculty> getFacultyByColor(String color) {
        logger.info("Method 'getFacultyByColor' was called");
        return facultyRepository.findByColor(color);
    }

    public Collection<Faculty> getAllFaculties() {
        logger.info("Method 'getAllFaculties' was called");
        return facultyRepository.findAll();
    }

    public Faculty findByNameOrColor(String string) {
        logger.info("Method 'findByNameOrColor' was called");
        Faculty faculty;
        if (string != null && !string.isBlank()) {
            faculty = facultyRepository.findByNameIgnoreCase(string);
            if (faculty != null) {
                return faculty;
            }
            faculty = facultyRepository.findByColorIgnoreCase(string);
            return faculty;
        }
        return null;
    }

    public Collection<Student> getStudents(long id) {
        logger.info("Method 'getStudents' was called");
        return studentRepository.findByFacultyId(id);
    }

    public String getLongestFacultyName() {
        return facultyRepository.findAll()
                .stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElseThrow(FacultyIsNotFoundException::new);
    }
}
