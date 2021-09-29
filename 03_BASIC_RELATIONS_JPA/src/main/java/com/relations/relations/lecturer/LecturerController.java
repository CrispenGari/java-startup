package com.relations.relations.lecturer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/lecturers")
@RequiredArgsConstructor
public class LecturerController {
    private final LecturerService service;

    @GetMapping("/one/{id}")
    public ResponseEntity<Lecturer> getLecturer(@PathVariable("id") Long id){
        return ResponseEntity.status(200).body(this.service.getLecturer(id)) ;
    }

    @PostMapping("/add")
    public ResponseEntity<Lecturer> createLecturer(@RequestBody Lecturer lecturer){
        return  ResponseEntity.status(200).body(this.service.createLecture(lecturer));
    }
}
