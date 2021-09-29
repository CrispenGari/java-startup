package com.relations.relations.course;

import com.relations.relations.lecturer.Lecturer;
import com.relations.relations.lecturer.LecturerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseService implements CourseServiceInterface{

    public final CourseRepository courseRepository;
    public final LecturerRepository lecturerRepository;
    @Override
    public Course addCourse(Course course) {
        return  this.courseRepository.save(course);
    }

    @Override
    public Course getCourse(Long id) {
        return this.courseRepository.findById(id).get();
    }

    @Override
    public Course assignLecturer(Long lecturerId, Long courseId) {
        // find the course
        Course course = this.getCourse(courseId);
        Lecturer lecturer = this.lecturerRepository.findById(lecturerId).get();
        course.setLecturer(lecturer);
        return this.courseRepository.save(course);
    }
}
