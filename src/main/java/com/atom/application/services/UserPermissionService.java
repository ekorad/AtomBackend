package com.atom.application.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import com.atom.application.models.UserPermission;
import com.atom.application.repos.UserPermissionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <b>User permissions service</b>
 * <p>
 * This service is one layer of abstraction above the user permission repository
 * (<code>UserPermissionRepository</code>) and provides basic <i>CRUD</i>
 * (<b>C</b>reate, <b>R</b>ead, <b>U</b>pdate, <b>D</b>elete) functionality for
 * user permissions through the use of an internal
 * <code>UserPermissionRepository</code>.
 * <p>
 * Besides the functionality offered by the underlying repository, the service
 * also performs minimal error checking.
 * <p>
 * The service does not expose any operations which have no real use for the
 * application.
 * 
 * @see {@link com.atom.application.repos.UserPermissionRepository
 *      UserPermissionRepository}
 * @see {@link com.atom.application.models.UserPermission UserPermission}
 */
@Service
public class UserPermissionService {

    /**
     * <b>The user permissions repository</b>
     * <p>
     * Used for direct access to the persisted user permissions.
     */
    @Autowired
    private UserPermissionRepository repo;

    /**
     * Retrieves all of the existing user permissions.
     * 
     * @return all of the existing user permissions, or an empty <code>List</code>
     *         if none exist
     */
    public List<UserPermission> getAllPermissions() {
        return repo.findAll();
    }

    /**
     * Retrieves all of the user permissions whose names match a given list of
     * requested names.
     * 
     * @param requestedPermissionNames - the names of the requested user permissions
     * @return the requested user permissions
     * @throws EntityNotFoundException if not all of the requested user permissions
     *                                 can be found
     */
    public List<UserPermission> getAllPermissionsByNames(List<String> requestedPermissionNames) {
        List<UserPermission> storedPermissions = repo.findAllByNames(requestedPermissionNames);
        List<String> storedPermissionsNames = storedPermissions.stream().map(UserPermission::getName)
                .collect(Collectors.toList());
        requestedPermissionNames.removeAll(storedPermissionsNames);
        if (!requestedPermissionNames.isEmpty()) {
            String namesNotFoundString = requestedPermissionNames.stream().map(name -> "'" + name + "'")
                    .collect(Collectors.joining(", "));
            String excMessage = (requestedPermissionNames.size() == 1) ? ("No user permission found with name: ")
                    : ("No user permissions found with names: ");
            excMessage += namesNotFoundString;
            throw new EntityNotFoundException(excMessage);
        }
        return storedPermissions;
    }

}
