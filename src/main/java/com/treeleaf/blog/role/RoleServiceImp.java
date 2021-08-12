package com.treeleaf.blog.role;

import com.treeleaf.blog.exception.InternalServerErrorException;
import com.treeleaf.blog.exception.ResourceNotFoundException;
import com.treeleaf.blog.exception.RoleNameAlreadyExistsException;
import com.treeleaf.blog.util.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImp  implements  RoleService{

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    Translator translator;

    /**
     * Check if name already exists
     *
     * @param name
     * @return
     */
    private boolean nameExists(String name){
        return roleRepository.findByName(name) != null ;
    }

    @Override
    public List<Role> getList() {

        return roleRepository.findAll();
    }

    @Override
    public void store(RoleRequest request) {
        if(nameExists(request.getName())){
            throw  new RoleNameAlreadyExistsException(translator.toLocale("error_role_already_exists"));
        }
        try{
            Role role = new Role();
            role.setName(request.getName());
            roleRepository.save(role);
        }catch(Exception e){
            throw  new InternalServerErrorException(translator.toLocale("error_internal_server"));
        }
    }

    @Override
    public void update(Long id, RoleRequest request) {
        Role role = roleRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(translator.toLocale("error_role_not_found"))
        );
        if(nameExists(request.getName())){
            throw  new RoleNameAlreadyExistsException(translator.toLocale("error_role_already_exists"));
        }

        try{
            role.setName(request.getName());
            roleRepository.save(role);
        }catch(Exception e){
            throw new InternalServerErrorException(translator.toLocale("error_internal_server"));
        }
    }

    @Override
    public void delete(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(translator.toLocale("error_role_not_found"))
        );

        roleRepository.delete(role);
    }
}
