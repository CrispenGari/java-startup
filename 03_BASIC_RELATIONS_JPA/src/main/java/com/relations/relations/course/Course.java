package com.relations.relations.course;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.relations.relations.lecturer.Lecturer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course implements Serializable {
    @SequenceGenerator(
            name="post_sequence",
            allocationSize = 1,
            sequenceName = "post_sequence"
    )
    @GeneratedValue(
            generator = "post_sequence",
            strategy = GenerationType.SEQUENCE
    )
    @Id
    private Long id;
    @Column(nullable = false, columnDefinition = "VARCHAR(7) NOT NULL")
    private String code;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="lecturer_id", referencedColumnName = "id")
    private Lecturer lecturer;

}
