package ru.hogwarts.school.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.models.Faculty;

import java.util.Collection;


public interface FacultyRepository extends JpaRepository<Faculty,Long> {
    Collection<Faculty> findByColor(String color);
    Faculty findByNameIgnoreCase(String Color);
    Faculty findByColorIgnoreCase(String color);

}
