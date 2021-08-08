package com.treeleaf.blog.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.treeleaf.blog.role.Role;
import com.treeleaf.blog.userprofile.UserProfile;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(nullable = false, unique = true)
    private String email ;

    private String password ;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt ;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt ;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    @JsonIgnore
    private UserProfile profile;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    @JsonIgnore
    private Collection<Role> roles;
}
