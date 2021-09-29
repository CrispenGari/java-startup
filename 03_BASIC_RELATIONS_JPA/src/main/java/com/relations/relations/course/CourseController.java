package com.relations.relations.course;

import com.relations.relations.lecturer.Lecturer;
import com.relations.relations.lecturer.LecturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService service;
    private final LecturerService lecturerService;

    @GetMapping("/one/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable("id") Long id){
        return ResponseEntity.ok(this.service.getCourse(id));
    }

    @PostMapping("/add/{lecturerId}")
    public  ResponseEntity<Course> addCourse(@RequestBody Course course, @PathVariable("lecturerId") Long lecturerId){

        Lecturer lecturer = this.lecturerService.getLecturer(lecturerId);
        course.setLecturer(lecturer);
        return ResponseEntity.ok(this.service.addCourse(course));
    }

    @PutMapping("/assign-lecturer/{lecturerId}/{courseId}")
    public ResponseEntity<Course> assignLecturer(@PathVariable("lecturerId") Long lecturerId,
                                                 @PathVariable("courseId") Long courseId
                                                 ){
        return ResponseEntity.status(204).body(this.service.assignLecturer(lecturerId, courseId));
    }

}
