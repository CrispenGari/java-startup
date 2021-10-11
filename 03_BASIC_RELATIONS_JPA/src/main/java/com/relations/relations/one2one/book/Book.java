package com.relations.relations.one2one.book;

import com.relations.relations.one2one.picture.Picture;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="books")
@NoArgsConstructor
public class Book implements Serializable {
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
    private  String title;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="picture_id", nullable = false)
    private Picture picture;
}
