package com.relations.relations.lecturer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.relations.relations.course.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="lecturers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lecturer implements Serializable {
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
    @Column(nullable = false)
    private String name;

    @JsonIgnore
    @OneToOne(mappedBy = "lecturer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Course course;
}
