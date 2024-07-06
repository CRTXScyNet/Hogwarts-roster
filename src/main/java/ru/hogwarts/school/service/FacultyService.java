package ru.hogwarts.school.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
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

//    @PostConstruct
//    private void init() {
//        addFaculty(new Faculty(1L, "Griffindor", "orange"));
//        addFaculty(new Faculty(2L, "Hufflepuff", "yellow"));
//        addFaculty(new Faculty(3L, "Ravenclaw", "blue"));
//        addFaculty(new Faculty(4L, "Slytherin", "green"));
//
//    }

    public Faculty getFaculty(Long id) {

        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty updateFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty deleteFaculty(Long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    public Collection<Faculty> getFacultyByColor(String color) {
        return facultyRepository.findByColor(color);
    }

    public Collection<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }
    public Faculty findByNameOrColor(String string){
        Faculty faculty;
        if (string != null && !string.isBlank()){
            faculty = facultyRepository.findByNameIgnoreCase(string);
            if (faculty!=null){
                return faculty;
            }
            faculty = facultyRepository.findByColorIgnoreCase(string);
            return faculty;
        }
        return null;
    }
    public Collection<Student> getStudents(long id){
        Faculty faculty = facultyRepository.findById(id).orElse(null);
        if(faculty == null){
            return null;
        }
        return faculty.getStudents();
    }
}
