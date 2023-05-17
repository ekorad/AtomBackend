package com.atom.application.repos;

import java.util.Collection;
import java.util.List;

import com.atom.application.models.UserPermission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * <b>User permissions repository</b>
 * <p>
 * Acts an interface between the database and the higher-level components and
 * provides direct <i>CRUD</i> (<b>C</b>reate, <b>R</b>ead, <b>U</b>pdate,
 * <b>D</b>elete) operations for user permissions.
 * <p>
 * This custom repository implementation provides the additional
 * <code>findAllByNames</code> operation besides the ones defined by the
 * <code>JpaRepository</code>.
 * 
 * @see {@link org.springframework.data.jpa.repository.JpaRepository
 *      JpaRepository}
 * @see {@link com.atom.application.models.UserPermission UserPermission}
 */
public interface UserPermissionRepository extends JpaRepository<UserPermission, Long> {

    /**
     * Retrieves a list of all user permissions whose names match a given list of
     * requested names.
     * <p>
     * The function will return a <code>List</code> of user permissions regardless
     * of whether or not all requested permissions are found.
     * 
     * @param names - the names of the requested user permissions
     * @return the requested user permissions, or an empty <code>List</code> if no
     *         such permissions found
     */
    @Query("SELECT p FROM UserPermission p WHERE p.name IN (:names)")
    public List<UserPermission> findAllByNames(@Param("names") Collection<String> names);

}
