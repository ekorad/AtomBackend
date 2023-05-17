package com.atom.application.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import com.atom.application.models.UserRole;
import com.atom.application.repos.UserRoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <b>User roles service</b>
 * <p>
 * This service is one layer of abstraction above the user role repository
 * (<code>UserRoleRepository</code>) and provides basic <i>CRUD</i>
 * (<b>C</b>reate, <b>R</b>ead, <b>U</b>pdate, <b>D</b>elete) functionality for
 * user roles through the use of an internal <code>UserRoleRepository</code>.
 * <p>
 * Besides the functionality offered by the underlying repository, the service
 * also performs minimal error checking.
 * <p>
 * The service does not expose any operations which have no real use for the
 * application.
 * 
 * @see {@link com.atom.application.repos.UserRoleRepository UserRoleRepository}
 * @see {@link com.atom.application.models.UserRole UserRole}
 */
@Service
public class UserRoleService {

    /**
     * <b>Names of read-only user roles</b>
     * <p>
     * This array provides the names of user roles which the application should
     * ensure are immutable.
     * <p>
     * The <code>USER</code>, <code>MODERATOR</code> and <code>ADMIN</code> user
     * roles should be read-only (immutable) and must not be altered by anyone
     * except developers.
     */
    private final static String READONLY_ROLES[] = { "USER", "MODERATOR", "ADMIN" };

    /**
     * <b>The user role repository</b>
     * <p>
     * Used for direct access to the persisted user roles.
     */
    @Autowired
    private UserRoleRepository repo;

    /**
     * Retrieves all of the existing user roles.
     * 
     * @return all of the existing user roles, or an empty <code>List</code> if none
     *         exist
     */
    public List<UserRole> getAllRoles() {
        return repo.findAll();
    }

    /**
     * Allows the persistence of a newly created user role.
     * 
     * @param newRole - the user role to be persisted
     * @return the user role that has been successfully persisted
     * @throws org.springframework.dao.DataIntegrityViolationException can be caused
     *                                                                 by
     *                                                                 {@link org.hibernate.exception.ConstraintViolationException},
     *                                                                 {@link org.hibernate.PropertyValueException}
     *                                                                 or
     *                                                                 {@link org.hibernate.exception.DataException}
     */
    public UserRole addNewRole(UserRole newRole) {
        return repo.save(newRole);
    }

    /**
     * Retrieves a single user role whose name matches the requested name.
     * 
     * @param requestedRoleName - the name of the requested user role
     * @return the requested user role
     * @throws javax.persistence.EntityNotFoundException if no user role is found
     *                                                   for the requested name
     */
    public UserRole getUserRoleByName(String requestedRoleName) {
        Optional<UserRole> persistedRequestedRoleOpt = repo.findByName(requestedRoleName);
        if (!persistedRequestedRoleOpt.isPresent()) {
            throw new EntityNotFoundException("No user role found with name: '" + requestedRoleName + "'");
        }
        UserRole persistedRequestedRole = persistedRequestedRoleOpt.get();
        return persistedRequestedRole;
    }

    /**
     * Retrieves all of the user roles whose names match a given list of requested
     * names.
     * 
     * @param requestedRoleNames - the names of the requested user roles
     * @return the requested user roles
     * @throws javax.persistence.EntityNotFoundException if not all of the requested
     *                                                   user roles can be found
     */
    public List<UserRole> getAllUserRolesByNames(List<String> requestedRoleNames) {
        List<UserRole> persistedRequestedRoles = repo.findAllByNames(requestedRoleNames);
        List<String> persistedRequestedRoleNames = persistedRequestedRoles.stream().map(UserRole::getName)
                .collect(Collectors.toList());
        requestedRoleNames.removeAll(persistedRequestedRoleNames);
        if (!requestedRoleNames.isEmpty()) {
            String namesNotFoundString = requestedRoleNames.stream().map(name -> "'" + name + "'")
                    .collect(Collectors.joining(", "));
            String excMessage = (requestedRoleNames.size() == 1) ? ("No user role found with name: ")
                    : ("No user roles found with names: ");
            excMessage += namesNotFoundString;
            throw new EntityNotFoundException(excMessage);
        }
        return persistedRequestedRoles;
    }

    /**
     * Removes the specified user role entities.
     * 
     * @param requestedRoles - the user roles that are requested to be deleted
     * @throws java.lang.IllegalArgumentException if at least one of the roles
     *                                            requested for deletion is marked
     *                                            as read-only by the
     *                                            <code>READONLY_ROLES</code> field
     */
    public void removeRoles(List<UserRole> requestedRoles) {
        List<String> requestedRoleNames = requestedRoles.stream().map(UserRole::getName).collect(Collectors.toList());
        List<String> readonlyNames = new ArrayList<>(Arrays.asList(READONLY_ROLES));
        readonlyNames.removeAll(requestedRoleNames);
        if (readonlyNames.size() != READONLY_ROLES.length) {
            List<String> requestedReadonlyNames = new ArrayList<>(Arrays.asList(READONLY_ROLES));
            requestedReadonlyNames.removeAll(readonlyNames);
            String requestedReadonlyNamesStr = requestedReadonlyNames.stream().collect(Collectors.joining(", "));
            String excMessage = (requestedReadonlyNames.size() == 1) ? ("Cannot remove read-only user role with name: ")
                    : ("Cannot remove read-only user roles with names: ");
            excMessage += requestedReadonlyNamesStr;
            throw new IllegalArgumentException(excMessage);
        }
        repo.deleteInBatch(requestedRoles);
    }

    /**
     * Updates a single user role.
     * <p>
     * The function does not update the ID of the user role.
     * 
     * @param existingRoleName - the name of the user role that will be updated
     * @param updatedRole      - the user role that will be used for the update
     * @return the updated user role
     * @throws java.lang.IllegalArgumentException        if the role requested for
     *                                                   update is marked as
     *                                                   read-only by the
     *                                                   <code>READONLY_ROLES</code>
     *                                                   field
     * @throws javax.persistence.EntityNotFoundException if the user role requested
     *                                                   for update cannot be found
     */
    public UserRole updateRole(String existingRoleName, UserRole updatedRole) {
        List<String> readonlyNames = Arrays.asList(READONLY_ROLES);
        if (readonlyNames.contains(existingRoleName)) {
            throw new IllegalArgumentException(
                    "Cannot modify read-only user role with name: '" + existingRoleName + "'");
        }

        Optional<UserRole> oldRoleOpt = repo.findByName(existingRoleName);
        if (!oldRoleOpt.isPresent()) {
            throw new EntityNotFoundException("Could not find user role with name: '" + existingRoleName + "'");
        }
        UserRole oldRole = oldRoleOpt.get();
        oldRole.setDescription(updatedRole.getDescription());
        oldRole.setName(updatedRole.getName());
        oldRole.setPermissions(updatedRole.getPermissions());
        return repo.save(oldRole);
    }

}
