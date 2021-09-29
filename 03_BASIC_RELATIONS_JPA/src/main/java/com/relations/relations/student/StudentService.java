package com.relations.relations.student;

import com.relations.relations.course.Course;
import com.relations.relations.course.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentService implements StudentServiceInterface{

    private final StudentRepository studentRepository;
    private  final CourseRepository courseRepository;
    @Override
    public Student addStudent(Student student) {
        return this.studentRepository.save(student);
    }

    @Override
    public Student addCourse(Long studentId, Long courseId) {
        Course course = this.courseRepository.findById(courseId).get();
        return null;
    }
}
