package com.treeleaf.blog.register;

import com.treeleaf.blog.exception.UserAlreadyExistException;
import com.treeleaf.blog.exception.UserRegisterStoredException;
import com.treeleaf.blog.user.User;
import com.treeleaf.blog.user.UserRepostitory;
import com.treeleaf.blog.userprofile.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegisterSerivceImp implements  UserRegisterSerivce {

    @Autowired
    private UserRepostitory userRepostitory;

    /**
     * Check if email already exists or not
     *
     * @param email
     * @return
     */
    private boolean emailExists(String email){
        return userRepostitory.findByEmail(email) != null ;
    }

    /**
     * Register the new user in the system
     *
     * @param request
     */
    @Override
    public void register(UserRegisterRequest request) {

        if(emailExists(request.getEmail())){
            throw new UserAlreadyExistException("The email address is already used.");
        }

        try{
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(request.getPassword());

            User user = new User();
            user.setEmail(request.getEmail());
            user.setPassword(encodedPassword);

            UserProfile userProfile = new UserProfile();
            userProfile.setFirstName(request.getFirstName());
            userProfile.setLastName(request.getLastName());

            user.setProfile(userProfile);
            userProfile.setUser(user);

            userRepostitory.save(user);
        }catch(Exception e){
            throw new UserRegisterStoredException("Internal error. We are working actively to fix the error");
        }

    }
}
