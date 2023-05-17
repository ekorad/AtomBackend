package com.atom.application.repos;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.atom.application.models.UserRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * <b>User role repository</b>
 * <p>
 * Acts an interface between the database and the higher-level components and
 * provides direct <i>CRUD</i> (<b>C</b>reate, <b>R</b>ead, <b>U</b>pdate,
 * <b>D</b>elete) operations for user roles.
 * <p>
 * This custom repository implementation provides the additional
 * <code>findByName</code> and <code>findAllByNames</code> operations besides
 * the ones defined by the <code>JpaRepository</code>.
 * 
 * @see {@link org.springframework.data.jpa.repository.JpaRepository
 *      JpaRepository}
 * @see {@link com.atom.application.models.UserRole UserRole}
 */
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    /**
     * Retrieves a single user role whose name matches the requested name.
     * <p>
     * If no matching user role is found, the returned <code>Optional</code> will be
     * empty.
     * 
     * @param name - the name of the requested user role
     * @return the requested user role wrapped in an <code>Optional</code>
     */
    public Optional<UserRole> findByName(String name);

    /**
     * Retrieves a list of all user roles whose names match a given list of
     * requested names.
     * <p>
     * The function will return a <code>List</code> of user roles regardless of
     * whether or not all requested roles are found.
     * 
     * @param names - the names of the requested user roles
     * @return the requested user roles, or an empty <code>List</code> if no such
     *         roles are found
     */
    @Query("SELECT r FROM UserRole r WHERE r.name IN (:names)")
    public List<UserRole> findAllByNames(@Param("names") Collection<String> names);

}
