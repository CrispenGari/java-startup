package com.relations.relations.one2many.student;
import java.util.Collection;

public interface StudentServiceInterface {
    Student createStudent(Student student);
    Student getStudent(Long id);
    Collection<Student> getAllStudents();
}
