package com.treeleaf.blog.user;

import com.treeleaf.blog.role.Role;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collection;
import java.util.Set;

public interface ProfileView {

    Long getId();

    String getEmail();

    @Value("#{target.profile.firstName+' '+target.profile.lastName}")
    String getFullName();

    Set<Role> getRoles();
}
