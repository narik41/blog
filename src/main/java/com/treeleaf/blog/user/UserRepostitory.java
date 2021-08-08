package com.treeleaf.blog.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepostitory extends JpaRepository<User, Long> {

    User findByEmail(String email);

    ProfileView findAllProjectedByEmail(String email);
}
