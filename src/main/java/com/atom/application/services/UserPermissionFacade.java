package com.atom.application.services;

import java.util.List;
import java.util.stream.Collectors;

import com.atom.application.dtos.UserPermissionDTO;
import com.atom.application.mappers.UserPermissionMapper;
import com.atom.application.models.UserPermission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <b>User permission facade service</b>
 * <p>
 * This is a "facade" service, which implies that it wraps the usual service
 * (<code>UserPermissionService</code>) and offers the same functionality, but
 * also has the responsability of mapping the entities
 * (<code>UserPermission</code>) to the corresponding DTOs
 * (<code>UserPermissionDTO</code>) and vice-versa.
 * <p>
 * To achieve this, it makes use of a <code>UserPermissionService</code> and a
 * <code>UserPermissionMapper</code>.
 * 
 * @see {@link com.atom.application.services.UserPermissionService
 *      UserPermissionService}
 * @see {@link com.atom.application.mappers.UserPermissionMapper
 *      UserPermissionMapper}
 * @see {@link com.atom.application.models.UserPermission UserPermission}
 * @see {@link com.atom.application.dtos.UserPermissionDTO UserPermissionDTO}
 */
@Service
public class UserPermissionFacade {

    /**
     * The service used for accessing the perissted user permission entities.
     */
    @Autowired
    private UserPermissionService service;
    /**
     * The mapper used for mapping between user permission entities
     * (<code>UserPermission</code>) and DTOs (<code>UserPermissionDTO</code>).
     */
    @Autowired
    private UserPermissionMapper mapper;

    /**
     * Retrieves a list of all existing user permissions, mapped as DTOs.
     * 
     * @return a <code>List</code> of all persisted user permissions, mapped as DTOs
     *         (<code>UserPermissionDTO</code>)
     * @see {@link com.atom.application.services.UserPermissionService#getAllPermissions()
     *      UserPermissionService.getAllPermissions()}
     */
    public List<UserPermissionDTO> getAllUserPermissions() {
        List<UserPermission> entities = service.getAllPermissions();
        return entities.stream().map(mapper::mapToDto).collect(Collectors.toList());
    }

    /**
     * Retrieves a list of all existing user permissions whose names match a given
     * list of names, mapped as DTOs.
     * 
     * @param requestedPermissionNames a <code>List</code> of user permission names
     *                                 which are being searched for
     * @return a <code>List</code> of all the existing user permissions with
     *         matching names, mapped as DTOs (<code>UserPermissionDTO</code>)
     */
    public List<UserPermissionDTO> getAllUserPermissionsByNames(List<String> requestedPermissionNames) {
        List<UserPermission> entities = service.getAllPermissionsByNames(requestedPermissionNames);
        return entities.stream().map(mapper::mapToDto).collect(Collectors.toList());
    }

}
