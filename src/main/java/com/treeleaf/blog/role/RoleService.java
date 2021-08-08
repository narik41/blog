package com.treeleaf.blog.role;

import java.util.List;

public interface RoleService {

    /**
     * Get a list of roles
     *
     */
    public List<Role> getList();

    /**
     * Store the details of role
     *
     * @param request
     */
    public void store(RoleRequest request);

    /**
     * Update the role details
     *
     * @param id
     * @param request
     */
    public void update(Long id, RoleRequest request);

    /**
     * Remove the role details
     *
     * @param id
     */
    public void delete(Long id);
}
