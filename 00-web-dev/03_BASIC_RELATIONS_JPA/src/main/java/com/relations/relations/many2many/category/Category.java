package com.relations.relations.many2many.category;
import com.relations.relations.many2many.common.AuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="categories")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Category extends AuditModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String category;


}
