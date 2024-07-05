package ru.hogwarts.school.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Collection;

@Service
public class FacultyService {
    @Autowired
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository repository) {
        this.facultyRepository = repository;
    }

    public Faculty addFaculty(Faculty faculty) {
//        faculty.setId(nextId++);
//        facultyMap.put(faculty.getId(),faculty);
        return facultyRepository.save(faculty);
    }

    @PostConstruct
    private void init() {
        addFaculty(new Faculty(1L, "Griffindor", "orange"));
        addFaculty(new Faculty(2L, "Hufflepuff", "yellow"));
        addFaculty(new Faculty(3L, "Ravenclaw", "blue"));
        addFaculty(new Faculty(4L, "Slytherin", "green"));

    }

    public Faculty getFaculty(Long id) {

        return facultyRepository.findById(id).get();
    }

    public Faculty updateFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty deleteFaculty(Long id) {
        boolean facultyDoesNotExist = facultyRepository.findById(id).isEmpty();

        if (facultyDoesNotExist) {
            return null;
        } else {
            Faculty faculty = facultyRepository.findById(id).get();
            facultyRepository.deleteById(id);
            return faculty;
        }
    }

    public Collection<Faculty> getFacultyByColor(String color) {
        return facultyRepository.findByColor(color);
    }

    public Collection<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }
}
