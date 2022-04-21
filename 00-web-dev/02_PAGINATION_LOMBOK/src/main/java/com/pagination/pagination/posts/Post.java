package com.pagination.pagination.posts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
@Table(name="posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post implements Serializable {
    @Id
    @SequenceGenerator(
            name="post_sequence",
            allocationSize = 1,
            sequenceName = "post_sequence"
    )
    @GeneratedValue(
            generator = "post_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    @Column(name = "caption", nullable = false)
    private String caption;
    @Column(nullable = false)
    private Status status;
    @Column(unique = true)
    @NotEmpty(message = "the email address is required")
    private String email;
}
