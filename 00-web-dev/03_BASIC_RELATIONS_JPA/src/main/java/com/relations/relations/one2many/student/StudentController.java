package com.relations.relations.one2many.student;

import com.relations.relations.one2many.lecturer.Lecturer;
import com.relations.relations.one2many.lecturer.LecturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/api/v1/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final LecturerService lecturerService;
    @PostMapping("/create/{lecturerId}")
    public ResponseEntity<Student> createLecturer(
            @PathVariable("lecturerId") Long lecturerId,
            @RequestBody Student student
    ){
        Lecturer lecturer = this.lecturerService.getLecturer(lecturerId);
        student.setLecturer(lecturer);
        return ResponseEntity.status(200).body(this.studentService.createStudent(student));
    }

    @GetMapping("/all")
    public ResponseEntity<Collection<Student>> getStudents(){
        return ResponseEntity.status(200).body(this.studentService.getAllStudents());
    }

    @GetMapping("/one/{studentId}")
    public ResponseEntity<Student> getLecturers(@PathVariable("studentId") Long studentId){
        return ResponseEntity.status(200).body(this.studentService.getStudent(studentId));
    }
}
