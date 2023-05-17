package com.atom.application.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import com.atom.application.models.WebUser;
import com.atom.application.repos.WebUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <b>Web users service</b>
 * <p>
 * This service is one layer of abstraction above the user role repository
 * (<code>WebUserRepository</code>) and provides basic <i>CRUD</i>
 * (<b>C</b>reate, <b>R</b>ead, <b>U</b>pdate, <b>D</b>elete) functionality for
 * web users through the use of an internal <code>WebUserRepository</code>.
 * <p>
 * Besides the functionality offered by the underlying repository, the service
 * also performs minimal error checking.
 * <p>
 * The service does not expose any operations which have no real use for the
 * application.
 * 
 * @see {@link com.atom.application.repos.WebUserRepository WebUserRepository}
 * @see {@link com.atom.application.models.WebUser WebUser}
 */
@Service
public class WebUserService {

    /**
     * <b>The web user repository</b>
     * <p>
     * Used for direct access to the persisted web users.
     */
    @Autowired
    private WebUserRepository repo;

    /**
     * Retrieves all of the existing web users.
     * 
     * @return all of the existing web users, or an empty <code>List</code> if none
     *         exist
     */
    public List<WebUser> getAllWebUsers() {
        return repo.findAll();
    }

    /**
     * Retrieves all of the web users whose usernames match a given list of
     * requested usernames.
     * 
     * @param requestedUsernames - the names of the requested web users
     * @return the requested web users
     * @throws javax.persistence.EntityNotFoundException if not all of the requested
     *                                                   web users can be found
     */
    public List<WebUser> getAllWebUsersByUsernames(List<String> requestedUsernames) {
        List<WebUser> persistedRequestedUsers = repo.findAllByUsernames(requestedUsernames);
        List<String> persistedRequestedUsernames = persistedRequestedUsers.stream().map(WebUser::getUsername)
                .collect(Collectors.toList());
        requestedUsernames.removeAll(persistedRequestedUsernames);
        if (!requestedUsernames.isEmpty()) {
            String namesNotFoundString = requestedUsernames.stream().map(name -> "'" + name + "'")
                    .collect(Collectors.joining(", "));
            String excMessage = (requestedUsernames.size() == 1) ? ("No web user found with username: ")
                    : ("No web users found with usernames: ");
            excMessage += namesNotFoundString;
            throw new EntityNotFoundException(excMessage);
        }
        return persistedRequestedUsers;
    }

    /**
     * Retrieves a single web user whose username matches the requested username.
     * 
     * @param requestedUsername - the username of the requested web user
     * @return the requested web user
     * @throws javax.persistence.EntityNotFoundException if no web user is found for
     *                                                   the requested username
     */
    public WebUser getWebUserByUsername(String requestedUsername) {
        Optional<WebUser> persistedRequestedUserOpt = repo.findByUsername(requestedUsername);
        if (!persistedRequestedUserOpt.isPresent()) {
            throw new EntityNotFoundException("No web user found with username: '" + requestedUsername + "'");
        }
        WebUser persistedRequestedUser = persistedRequestedUserOpt.get();
        return persistedRequestedUser;
    }

    /**
     * Retrieves a single web user whose email matches the requested email.
     * 
     * @param requestedEmail - the email of the requested web user
     * @return the requested web user
     * @throws javax.persistence.EntityNotFoundException if no web user is found for
     *                                                   the requested email
     */
    public WebUser getWebUserByEmail(String requestedEmail) {
        Optional<WebUser> persistedRequestedUserOpt = repo.findByEmail(requestedEmail);
        if (!persistedRequestedUserOpt.isPresent()) {
            throw new EntityNotFoundException("No user account found with email: '" + requestedEmail + "'");
        }
        WebUser persistedRequestedUser = persistedRequestedUserOpt.get();
        return persistedRequestedUser;
    }

    /**
     * Allows the persistence of a newly created web user.
     * 
     * @param newUser - the web user to be persisted
     * @return the web user that has been successfully persisted
     * @throws org.springframework.dao.DataIntegrityViolationException can be caused
     *                                                                 by
     *                                                                 {@link org.hibernate.exception.ConstraintViolationException},
     *                                                                 {@link org.hibernate.PropertyValueException}
     *                                                                 or
     *                                                                 {@link org.hibernate.exception.DataException}
     */
    public WebUser addNewWebUser(WebUser newUser) {
        return repo.save(newUser);
    }

    /**
     * Removes the specified web users.
     * 
     * @param requestedUsers - the web users that are requested to be deleted
     */
    public void removeWebUsers(List<WebUser> requestedUsers) {
        repo.deleteInBatch(requestedUsers);
    }

    /**
     * Updates a single web user.
     * <p>
     * The function does not update the ID of the web user.
     * 
     * @param existingUsername - the username of the web user that will be updated
     * @param updatedUser      - the web user that will be used for the update
     * @return the updated web user
     * @throws javax.persistence.EntityNotFoundException if the web user requested
     *                                                   for update cannot be found
     */
    public WebUser updateWebUser(String existingUsername, WebUser updatedUser) {
        Optional<WebUser> oldUserOpt = repo.findByUsername(existingUsername);
        if (!oldUserOpt.isPresent()) {
            throw new EntityNotFoundException("Could not find a web user with username: '" + existingUsername + "'");
        }
        WebUser oldUser = oldUserOpt.get();
        oldUser.setActivated(updatedUser.getActivated());
        oldUser.setEmail(updatedUser.getEmail());
        oldUser.setFirstName(updatedUser.getFirstName());
        oldUser.setLastName(updatedUser.getLastName());
        oldUser.setLocked(updatedUser.getLocked());
        oldUser.setPassword(updatedUser.getPassword());
        oldUser.setRole(updatedUser.getRole());
        oldUser.setUsername(updatedUser.getUsername());
        oldUser.setAddresses(updatedUser.getAddresses());
        oldUser.setPhoneNumbers(updatedUser.getPhoneNumbers());
        return repo.save(oldUser);
    }

    //get user by id
    public Optional<WebUser> getUserById(Long id) {
        return repo.findById(id);
    }

	

}
