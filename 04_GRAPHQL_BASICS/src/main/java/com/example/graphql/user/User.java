package com.example.graphql.user;
import com.example.graphql.common.AuditModel;
import com.example.graphql.post.Post;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends AuditModel implements Serializable {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    @Column(nullable = false, unique = true)
    private  String username;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "user")
    @Fetch(value= FetchMode.SELECT)
    private Collection<Post> posts;

    public User(String username) {
        this.username = username;
    }
}
