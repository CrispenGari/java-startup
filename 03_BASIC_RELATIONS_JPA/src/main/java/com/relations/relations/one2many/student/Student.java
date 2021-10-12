package com.relations.relations.one2many.student;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.relations.relations.one2many.common.AuditModel;
import com.relations.relations.one2many.lecturer.Lecturer;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="student")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Student extends AuditModel  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lecturer_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Lecturer lecturer;
}
