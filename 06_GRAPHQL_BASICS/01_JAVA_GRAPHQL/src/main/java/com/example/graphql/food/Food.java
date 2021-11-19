package com.example.graphql.food;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="foods")
@NoArgsConstructor
public class Food implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GraphQLQuery(name = "id", description = "A food's id")
    private Long id;

    @GraphQLQuery(name = "name", description = "A food's name")
    private String name;
}
