package com.treeleaf.blog.role;

import com.treeleaf.blog.exception.InternalServerErrorException;
import com.treeleaf.blog.exception.ResourceNotFoundException;
import com.treeleaf.blog.exception.RoleNameAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImp  implements  RoleService{

    @Autowired
    RoleRepository roleRepository;

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
            throw  new RoleNameAlreadyExistsException("Role already exists.");
        }
        try{
            Role role = new Role();
            role.setName(request.getName());
            roleRepository.save(role);
        }catch(Exception e){
            throw  new InternalServerErrorException("Internal error. We are working actively to fix the error");
        }
    }

    @Override
    public void update(Long id, RoleRequest request) {
        Role role = roleRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Role not found."));
        if(nameExists(request.getName())){
            throw  new RoleNameAlreadyExistsException("Role already exists.");
        }

        try{
            role.setName(request.getName());
            roleRepository.save(role);
        }catch(Exception e){
            throw  new InternalServerErrorException("Internal error. We are working actively to fix the error");
        }
    }

    @Override
    public void delete(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Role not found."));
        roleRepository.delete(role);
    }
}
