package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mapping.FactoryMethod;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

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
        logger.info("Был вызван метод 'addFaculty'");
        return facultyRepository.save(faculty);
    }


    public Faculty getFaculty(Long id) {
        logger.info("Был вызван метод 'getFaculty'");
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty updateFaculty(Faculty faculty) {
        logger.info("Был вызван метод 'updateFaculty'");
        return facultyRepository.save(faculty);
    }

    public Faculty deleteFaculty(Long id) {
        logger.info("Был вызван метод 'deleteFaculty'");
        Faculty faculty = facultyRepository.findById(id).orElse(null);
        if (faculty == null){
            return null;
        }
        facultyRepository.deleteById(id);
        return faculty;
    }

    public Collection<Faculty> getFacultyByColor(String color) {
        logger.info("Был вызван метод 'getFacultyByColor'");
        return facultyRepository.findByColor(color);
    }

    public Collection<Faculty> getAllFaculties() {
        logger.info("Был вызван метод 'getAllFaculties'");
        return facultyRepository.findAll();
    }

    public Faculty findByNameOrColor(String string) {
        logger.info("Был вызван метод 'findByNameOrColor'");
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
        logger.info("Был вызван метод 'getStudents'");
        return studentRepository.findByFacultyId(id);
    }
    public String getLongestFacultyName(){
        String s = facultyRepository.findAll().stream().map(Faculty::getName).max(Comparator.comparingInt(String::length)).get();
        if (s == null){
            logger.warn("Самое длинное название факультета не найдено. Возможно список факультетов пуст.");
        }
        return s;
    }
}
