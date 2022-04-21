package com.relations.relations.many2many.question;


import com.relations.relations.many2many.category.Category;
import com.relations.relations.many2many.common.AuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;


@Entity
@Table(name="questions")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Question extends AuditModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false)
    private String title;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "questions_categories",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Collection<Category> categories;
}
