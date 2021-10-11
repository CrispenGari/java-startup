package com.relations.relations.one2one.picture;
import com.relations.relations.one2one.book.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="pictures")
@NoArgsConstructor
@AllArgsConstructor
public class Picture implements Serializable {
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
    private String url;

    @OneToOne(mappedBy = "picture")
    private Book book;

    public Picture(String url){
        this.url = url;
    }
}
