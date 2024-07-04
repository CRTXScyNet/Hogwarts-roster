package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class StudentService {
    private final Map<Long, Student> students = new HashMap<>();

    private Long nextId = 0L;

    public Student addStudent(Student student){
        student.setId(nextId++);
        students.put(student.getId(),student);
        return student;
    }
    public Student getStudent(Long id){
        return students.get(id);
    }
    public Student updateStudent(Student student){
        return students.put(student.getId(),student);
    }

    public Student deleteStudent(Long id) {
        return students.remove(id);
    }

    public Collection<Student> getStudentByAge(int age) {
        return students.values().stream().filter(e -> e.getAge() == age).toList();
    }
}
