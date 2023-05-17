package com.atom.application.mappers;

import com.atom.application.dtos.UserPermissionDTO;
import com.atom.application.models.UserPermission;

import org.springframework.stereotype.Component;

/**
 * <b>Mapper implementation for user permissions</b>
 * <p>
 * This class deals with mapping between user permission entities
 * (<code>UserPermission</code>) and user permission DTOs
 * (<code>UserPermissionDTO</code>).
 * 
 * @see {@link com.atom.application.models.UserPermission UserPermission}
 * @see {@link com.atom.application.dtos.UserPermissionDTO UserPermissionDTO}
 */
@Component
public class UserPermissionMapper implements EntityDTOMapper<UserPermission, UserPermissionDTO> {

    /**
     * This function maps a user permission entity (<code>UserPermission</code>) to
     * the corresponding DTO (<code>UserPermissionDTO</code>).
     * <p>
     * The <code>id</code> of the user permission entity is also included into the
     * mapping.
     * 
     * @param entity - the user permission entity to be mapped to the DTO
     * @return the user permission DTO
     */
    @Override
    public UserPermissionDTO mapToDto(UserPermission entity) {
        UserPermissionDTO dto = new UserPermissionDTO();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setName(entity.getName());
        return dto;
    }

    /**
     * This function maps a user permission DTO (<code>UserPermissionDTO</code>) to
     * the corresponding entity (<code>UserPermission</code>).
     * <p>
     * The <code>id</code> of the user permission DTO is not mapped to the entity as
     * ids are automatically generated.
     * 
     * @deprecated - this function is implemented only for consistency and has no
     *             real use
     * @param dto - the DTO to be mapped to the entity
     * @return the entity
     */
    @Deprecated(forRemoval = true)
    @Override
    public UserPermission mapToEntity(UserPermissionDTO dto) {
        UserPermission entity = new UserPermission();
        entity.setDescription(dto.getDescription());
        entity.setName(dto.getName());
        return entity;
    }

}
