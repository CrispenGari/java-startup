package com.relations.relations.lecturer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LecturerService implements LecturerServiceInterface{
    private final LecturerRepository repository;
    @Override
    public Lecturer createLecture(Lecturer lecturer) {
        return this.repository.save(lecturer);
    }
    @Override
    public Lecturer getLecturer(Long id) {
        return this.repository.findById(id).get();
    }
}
