package com.treeleaf.blog.role;

import com.treeleaf.blog.common.APIResponse;
import com.treeleaf.blog.common.APIRoutes;
import com.treeleaf.blog.util.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    Translator translator;

    @GetMapping(APIRoutes.ROLE.GET_ROLES)
    public ResponseEntity<APIResponse> getRoles(){

        APIResponse apiResponse = new APIResponse();
        apiResponse.setMessage(translator.toLocale("success_role_list"));
        apiResponse.setData(roleService.getList());
        apiResponse.setStatus(HttpStatus.OK.value());

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping(APIRoutes.ROLE.STORE_ROLE)
    public ResponseEntity<APIResponse> store(@Valid RoleRequest request) {

        roleService.store(request);

        APIResponse apiResponse = new APIResponse();
        apiResponse.setMessage(translator.toLocale("success_role_added"));
        apiResponse.setStatus(HttpStatus.OK.value());

        return new ResponseEntity(apiResponse, HttpStatus.CREATED);
    }

    @PutMapping(APIRoutes.ROLE.SINGLE_ROLE)
    public ResponseEntity<APIResponse> update(@PathVariable("id") Long id, @Valid RoleRequest request){
        roleService.update(id, request);

        APIResponse apiResponse = new APIResponse();
        apiResponse.setMessage(translator.toLocale("success_role_updated"));
        apiResponse.setStatus(HttpStatus.OK.value());

        return new ResponseEntity(apiResponse, HttpStatus.CREATED);
    }

    @DeleteMapping(APIRoutes.ROLE.SINGLE_ROLE)
    public ResponseEntity<APIResponse> delete(@PathVariable("id") Long id){
        roleService.delete(id);

        APIResponse apiResponse = new APIResponse();
        apiResponse.setMessage(translator.toLocale("success_role_deleted"));
        apiResponse.setStatus(HttpStatus.OK.value());

        return new ResponseEntity(apiResponse, HttpStatus.OK);
    }
}
