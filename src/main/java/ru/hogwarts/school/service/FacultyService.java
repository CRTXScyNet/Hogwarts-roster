package ru.hogwarts.school.service;

import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final Map<Long, Faculty> facultyMap = new HashMap<>();
    private Long nextId = 0L;
    public Faculty addFaculty(Faculty faculty){
        faculty.setId(nextId++);
        facultyMap.put(faculty.getId(),faculty);
        return faculty;
    }
    @PostConstruct
    private void init(){
        addFaculty(new Faculty(0L,"Griffindor", "orange"));
        addFaculty(new Faculty(0L,"Hufflepuff", "yellow"));
        addFaculty(new Faculty(0L,"Ravenclaw", "blue"));
        addFaculty(new Faculty(0L,"Slytherin", "green"));

    }
    public Faculty getFaculty(Long id) {

        return facultyMap.get(id);
    }
    public Faculty updateFaculty(Faculty faculty){
        facultyMap.put(faculty.getId(), faculty);
        return faculty;
    }
    public Faculty deleteFaculty(Long id) {
        return facultyMap.remove(id);
    }
    public Collection<Faculty> getFacultyByColor(String color){
        return facultyMap.values().stream().filter(e -> e.getColor().equals(color)).toList();
    }
}
