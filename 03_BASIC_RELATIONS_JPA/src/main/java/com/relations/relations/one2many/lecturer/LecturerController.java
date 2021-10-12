package com.relations.relations.one2many.lecturer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/lecturers")
@RequiredArgsConstructor
public class LecturerController {
    
    private final LecturerService service;
    @PostMapping("/create")
    public ResponseEntity<Lecturer> createLecturer(@RequestBody Lecturer lecturer){
        return ResponseEntity.status(200).body(this.service.createLecturer(lecturer));
    }
    
    @GetMapping("/all")
    public ResponseEntity<Collection<Lecturer>> getLecturers(){
        return ResponseEntity.status(200).body(this.service.getAllLecturers());
    }

    @GetMapping("/one/{lecturerId}")
    public ResponseEntity<Lecturer> getLecturers(@PathVariable("lecturerId") Long lecturerId){
        return ResponseEntity.status(200).body(this.service.getLecturer(lecturerId));
    }
}
