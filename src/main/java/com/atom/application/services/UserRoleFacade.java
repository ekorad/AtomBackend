package com.atom.application.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import com.atom.application.dtos.UserRoleDTO;
import com.atom.application.mappers.UserRoleMapper;
import com.atom.application.models.UserRole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <b>User role facade service</b>
 * <p>
 * This is a "facade" service, which implies that it wraps the usual service
 * (<code>UserRoleService</code>) and offers the same functionality, but also
 * has the responsability of mapping the entities (<code>UserRole</code>) to the
 * corresponding DTOs (<code>UserRoleDTO</code>) and vice-versa.
 * <p>
 * To achieve this, it makes use of a <code>UserRoleService</code> and a
 * <code>UserRoleMapper</code>.
 * 
 * @see {@link com.atom.application.services.UserRoleService UserRoleService}
 * @see {@link com.atom.application.mappers.UserRoleMapper UserRoleMapper}
 * @see {@link com.atom.application.models.UserRole UserRole}
 * @see {@link com.atom.application.dtos.UserRoleDTO UserRoleDTO}
 */
@Service
public class UserRoleFacade {

    /**
     * <b>The user role service</b>
     * <p>
     * Used for performing CRUD operations on user role entities.
     */
    @Autowired
    private UserRoleService service;
    /**
     * <b>The entity to DTO mapper</b>
     * <p>
     * Used for mapping between user role entities (<code>UserRole</code>) and user
     * role DTOs (<code>UserRoleDTO</code>).
     */
    @Autowired
    private UserRoleMapper mapper;

    /**
     * Retrieves a list of all the existing user roles mapped as DTOs.
     * <p>
     * If no user role entities exist in the database, an empty <code>List</code> is
     * returned.
     * 
     * @return a <code>List</code> of all of the existing user role mapped as DTOs
     */
    public List<UserRoleDTO> getAllUserRoles() {
        List<UserRole> roleEntities = service.getAllRoles();
        return roleEntities.stream().map(mapper::mapToDto).collect(Collectors.toList());
    }

    /**
     * Allows the persistence of a newly created user role.
     * 
     * @param newRoleDTO - the DTO that will be mapped to an entity which will be
     *                   persisted
     */
    public void addNewUserRole(UserRoleDTO newRoleDTO) {
        UserRole roleEntity = mapper.mapToEntity(newRoleDTO);
        service.addNewRole(roleEntity);
    }

    /**
     * Updates a single user role entity.
     * 
     * @param existingRoleName - the name of the user role that will be updated
     * @param updatedRole      - the user role entity that will be used for the
     *                         update
     * @return the updated user role entity
     * @throws java.lang.IllegalArgumentException if the role requested for update
     *                                            is marked as read-only by the
     *                                            <code>READONLY_ROLES</code> field
     */

    /**
     * Updates a single user role.
     * 
     * @param existingRoleName - the name of the user role that will be updated
     * @param updatedRoleDTO   - the user role DTO that will be used for the update
     */
    public void updateExistingUserRole(String existingRoleName, UserRoleDTO updatedRoleDTO) {
        UserRole roleEntity = null;
        try {
            roleEntity = mapper.mapToEntity(updatedRoleDTO);
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundException(
                    "No user role found with the specified user permissions (" + ex.getLocalizedMessage() + ")");
        }
        service.updateRole(existingRoleName, roleEntity);
    }

    public void deleteUserRolesByName(List<String> existingRoleNames) {
        List<UserRole> persistedRoles = service.getAllUserRolesByNames(existingRoleNames);
        service.removeRoles(persistedRoles);
    }

}
