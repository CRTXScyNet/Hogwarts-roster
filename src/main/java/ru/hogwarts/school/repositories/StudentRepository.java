package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;


import java.util.Collection;
import java.util.List;


public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAge(int age);

    Collection<Student> findByAgeBetween(int min, int max);

    Collection<Student> findByFacultyId(long id);

    @Query(value = "SELECT faculty_id FROM student  WHERE id = ?1", nativeQuery = true)
    Long findFaculty_IdById(Long id);

    @Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    Integer getAmountOfStudents();
    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
    Integer getAverageAgeOfStudents();
@Query(value = "SELECT * FROM student ORDER BY id  OFFSET (SELECT COUNT(*) FROM student ) - 5 ", nativeQuery = true)
    Collection<Student> getLastFiveStudents();
}
