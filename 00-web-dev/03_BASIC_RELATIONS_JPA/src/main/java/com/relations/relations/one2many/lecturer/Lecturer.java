package com.relations.relations.one2many.lecturer;
import com.relations.relations.one2many.common.AuditModel;
import com.relations.relations.one2many.student.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name="lectures")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Lecturer extends AuditModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "lecturer")
    @Fetch(value= FetchMode.SELECT)
    private Collection<Student> students;

}
