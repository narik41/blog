package com.treeleaf.blog.userprofile;

import com.treeleaf.blog.user.User;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user_profiles")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(name = "first_name", nullable = false)
    private String firstName ;


    @Column(name = "last_name", nullable = false)
    private String lastName;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
