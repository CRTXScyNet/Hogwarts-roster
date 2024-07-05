package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculties")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService service) {
        this.facultyService = service;
    }
    @PostMapping
    public Faculty addFaculty(@RequestBody Faculty faculty) {

        return facultyService.addFaculty(faculty);
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.getFaculty(id);
        if (faculty == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(faculty);
    }
    @GetMapping()
    public Collection<Faculty> getFacultyByColor(@RequestParam String color){
        return facultyService.getFacultyByColor(color);
    }
    @GetMapping("all")
    public Collection<Faculty> getAllFaculty(){
        return facultyService.getAllFaculties();
    }
    @PutMapping
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty exixtedFaculty = facultyService.getFaculty(faculty.getId());
        if (exixtedFaculty == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(facultyService.updateFaculty(faculty));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.deleteFaculty(id);
        if(faculty == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(faculty);
    }


}
