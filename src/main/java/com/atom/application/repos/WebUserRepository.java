package com.atom.application.repos;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.atom.application.models.WebUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * <b>Web user repository</b>
 * <p>
 * Acts an interface between the database and the higher-level components and
 * provides direct <i>CRUD</i> (<b>C</b>reate, <b>R</b>ead, <b>U</b>pdate,
 * <b>D</b>elete) operations for users.
 * <p>
 * This custom repository implementation provides the additional
 * <code>findByUsername</code>, <code>findByEmail</code> and
 * <code>findAllByUsernames</code> operations besides the ones defined by the
 * <code>JpaRepository</code>.
 * 
 * @see {@link org.springframework.data.jpa.repository.JpaRepository
 *      JpaRepository}
 * @see {@link com.atom.application.models.WebUser WebUser}
 */
public interface WebUserRepository extends JpaRepository<WebUser, Long> {

    /**
     * Retrieves a single user whose username matches the requested username.
     * <p>
     * If no matching user is found, the returned <code>Optional</code> will be
     * empty.
     * 
     * @param username - the username of the requested user
     * @return the requested user, wrapped in an <code>Optional</code>
     */
    public Optional<WebUser> findByUsername(String username);

    /**
     * Retrieves a single user whose e-mail address matches the requested e-mail
     * address.
     * <p>
     * If no matching user is found, the returned <code>Optional</code> will be
     * empty.
     * 
     * @param email - the e-mail address of the requested user
     * @return the requested user, wrapped in an <code>Optional</code>
     */
    public Optional<WebUser> findByEmail(String email);

    /**
     * Retrieves a list of all users whose usernames match a given list of requested
     * usernames.
     * <p>
     * The function will return a <code>List</code> of users regardless of whether
     * or not all requested users are found.
     * 
     * @param usernames - the usernames of the requested users
     * @return the requested users, or an empty <code>List</code> if no such users
     *         are found
     */
    @Query("SELECT u FROM WebUser u WHERE u.username IN (:usernames)")
    public List<WebUser> findAllByUsernames(@Param("usernames") Collection<String> usernames);

}
