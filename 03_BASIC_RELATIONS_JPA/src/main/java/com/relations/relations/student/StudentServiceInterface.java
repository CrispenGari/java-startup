package com.relations.relations.student;

public interface StudentServiceInterface {
    Student addStudent(Student student);
    Student addCourse(Long studentId, Long courseId);
}
