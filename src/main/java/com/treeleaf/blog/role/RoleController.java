package com.treeleaf.blog.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getRoles(){
        return new ResponseEntity<>(roleService.getList(), HttpStatus.OK);
    }

    @PostMapping("/role")
    public ResponseEntity<String> store(@Valid RoleRequest request) {

        roleService.store(request);

        return new ResponseEntity("Role stored successfully.", HttpStatus.CREATED);
    }

    @PutMapping("/role/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @Valid RoleRequest request){
        roleService.update(id, request);

        return new ResponseEntity("Role updated successfully.", HttpStatus.CREATED);
    }

    @DeleteMapping("/role/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        roleService.delete(id);

        return new ResponseEntity("Role deleted successfully.", HttpStatus.OK);
    }
}
