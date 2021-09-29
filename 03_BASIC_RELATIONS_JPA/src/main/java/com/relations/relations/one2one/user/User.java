package com.relations.relations.one2one.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.relations.relations.one2one.profile.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
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
    private String username;

    @JsonIgnore
    @OneToOne(targetEntity = Profile.class, mappedBy = "user", optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn()
    private Profile profile;
}
