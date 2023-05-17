package com.atom.application.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import com.atom.application.dtos.UserRoleDTO;
import com.atom.application.models.UserPermission;
import com.atom.application.models.UserRole;
import com.atom.application.services.UserPermissionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <b>Mapper implementation for mapping between entities and DTOs</b>
 * <p>
 * This class deals with the mapping between user role entities
 * (<code>UserRole</code>) and user role DTOs (<code>UserRoleDTO</code>).
 * 
 * @see {@link com.atom.application.models.UserRole UserRole}
 * @see {@link com.atom.application.dtos.UserRoleDTO UserRoleDTO}
 */
@Component
public class UserRoleMapper implements EntityDTOMapper<UserRole, UserRoleDTO> {

    /**
     * <b>The user permission service</b>
     * <p>
     * Used for retrieving the user permission entities required for the correct
     * mapping to an entity.
     */
    @Autowired
    private UserPermissionService service;

    /**
     * Maps a user role entity (<code>UserRole</code>) to the corresponding DTO
     * (<code>UserRoleDTO</code>).
     * <p>
     * In the DTO, the user permissions are stored as a <code>List</code> of user
     * permission names.
     * 
     * @param roleEntity - the user role entity which will be mapped to a user role
     *                   DTO
     * @return the user role DTO resulted from the mapping operation
     */
    @Override
    public UserRoleDTO mapToDto(UserRole roleEntity) {
        UserRoleDTO roleDTO = new UserRoleDTO();
        roleDTO.setId(roleEntity.getId());
        roleDTO.setName(roleEntity.getName());
        roleDTO.setDescription(roleEntity.getDescription());
        roleDTO.setPermissions(
                roleEntity.getPermissions().stream().map(UserPermission::getName).collect(Collectors.toSet()));
        return roleDTO;
    }

    /**
     * Maps a user role DTO (<code>UserRoleDTO</code>) to a user role entity
     * (<code>UserRole</code>).
     * <p>
     * In this case, the <code>id</code> of the user role DTO is not copied into the
     * entity, as ids are managed automatically.
     * 
     * @param roleDTO - the user role DTO that will be mapped to a user role entity
     * @return - the user role entity resulted from the mapping operation
     * @throws java.lang.IllegalArgumentException if at least one of the permissions
     *                                            contained in the DTO cannot be
     *                                            found
     */
    @Override
    public UserRole mapToEntity(UserRoleDTO roleDTO) {
        UserRole roleEntity = new UserRole();
        roleEntity.setDescription(roleDTO.getDescription());
        roleEntity.setName(roleDTO.getName());
        List<UserPermission> persistedPermissions = null;
        try {
            List<String> requestedPermissionNames = new ArrayList<>(roleDTO.getPermissions());
            persistedPermissions = service.getAllPermissionsByNames(requestedPermissionNames);
        } catch (EntityNotFoundException ex) {
            throw new IllegalArgumentException(
                    "Mapping failed: user role DTO contains permissions which cannot be found (" + ex.getLocalizedMessage() + ")", ex);
        }
        roleEntity.setPermissions(persistedPermissions.stream().collect(Collectors.toSet()));
        return roleEntity;
    }

}
