package com.relations.relations.one2many.student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Collection;
@Service
@RequiredArgsConstructor
@Transactional
public class StudentService implements StudentServiceInterface{
    private final StudentRepository repository;
    @Override
    public Student createStudent(Student student) {
        return this.repository.save(student);
    }

    @Override
    public Student getStudent(Long id) {
        return this.repository.findById(id).orElseThrow(()-> new RuntimeException("the student with that id does not exists."));
    }

    @Override
    public Collection<Student> getAllStudents() {
        return this.repository.findAll();
    }
}
