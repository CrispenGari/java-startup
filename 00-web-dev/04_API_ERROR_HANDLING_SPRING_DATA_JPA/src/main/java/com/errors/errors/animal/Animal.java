package com.errors.errors.animal;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="animals")
@Data
@NoArgsConstructor
public class Animal implements Serializable {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false, unique = false, length = 255)
    private  String name;
}
