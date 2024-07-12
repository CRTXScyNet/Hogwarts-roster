select s.name as student_name, s.age as students_age ,f.name as students_faculty
from student s
right join  faculty f
on s.faculty_id = f.id

select s.* from student s
right join avatar a
on s.id = a.student_id;