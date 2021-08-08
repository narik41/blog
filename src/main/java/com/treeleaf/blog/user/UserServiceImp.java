package com.treeleaf.blog.user;

import com.treeleaf.blog.util.LoggedinUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class UserServiceImp implements  UserService, UserDetailsService {

    @Autowired
    private UserRepostitory userRepostitory;

    @Autowired
    private LoggedinUser loggedinUser;

    @Override
    public UserDetails loadUserByUsername(String username){
        User user = userRepostitory.findByEmail(username);
        if(user ==null){
            throw new UsernameNotFoundException("User not found in the database");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role->{
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public ProfileView profile() {
        String email = loggedinUser.username();
        return  userRepostitory.findAllProjectedByEmail(email);
    }
}
