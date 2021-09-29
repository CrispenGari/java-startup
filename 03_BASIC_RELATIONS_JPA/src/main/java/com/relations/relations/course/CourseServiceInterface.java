package com.relations.relations.course;

import com.relations.relations.lecturer.Lecturer;

public interface CourseServiceInterface {
    Course addCourse(Course course);
    Course getCourse(Long id);
    Course assignLecturer(Long lecturerId, Long courseId);
}

