package com.relations.relations.one2many.lecturer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional
public class LecturerService implements LecturerServiceInterface{

    private final LecturerRepository repository;
    @Override
    public Lecturer createLecturer(Lecturer lecturer) {
        System.out.println(lecturer.getName());
        return this.repository.save(lecturer);
    }

    @Override
    public Lecturer getLecturer(Long id) {
        return this.repository.findById(id).orElseThrow(()-> new RuntimeException("the lecturer of that id does not exists."));
    }
    @Override
    public Collection<Lecturer> getAllLecturers() {
        return this.repository.findAll();
    }
}
