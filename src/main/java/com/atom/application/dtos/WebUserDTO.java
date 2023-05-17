package com.atom.application.dtos;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * <b>Web user DTO</b>
 * <p>
 * This is a DTO, which means that it stores a minimal set of properties from
 * the corresponding entity type, making it easier to transfer over HTTP.
 * <p>
 * As DTOs are objects used for transferring data between server and client,
 * validation is mandatory.
 * <p>
 * This DTO stores all the data from the web user entity (<code>WebUser</code>).
 * A special is the associated user role, which is stored only by its name.
 * <p>
 * The mapping operation is performed using a mapper object
 * (<code>WebUserMapper</code>).
 * 
 * @see {@link com.atom.application.models.WebUser WebUser}
 * @see {@link com.atom.application.mappers.WebUserMapper WebUserMapper}
 */
public class WebUserDTO {

    /**
     * <b>The ID of the web user</b>
     * <p>
     * For further details, see the definition of the
     * {@link com.atom.application.models.WebUser#id WebUser.id} field.
     * <p>
     * The <code>id</code> is mainly used for output purposes (from server to
     * client), as entity IDs are maintained automatically by Hibernate.
     * <p>
     * The <code>id</code> is not subject to validation as it is not required for a
     * successful transfer, as well as being able to take any numerical value.
     */
    private Long id;

    /**
     * <b>The first name of the web user</b>
     * <p>
     * For further details, see the definition of the
     * {@link com.atom.application.models.WebUser#firstName WebUser.firstName}
     * field.
     * <p>
     * The <code>firstName</code> is subject to validation as it is mandatory for
     * transfers and has constraints imposed on the characters it can contain and
     * it's size.
     */
    @NotBlank(message = "User first name is mandatory and cannot contain only whitespace")
    @Size(min = 2, max = 30, message = "User first name must contain between 2 and 30 valid characters")
    private String firstName;

    /**
     * <b>The last name of the web user</b>
     * <p>
     * For further details, see the definition of the
     * {@link com.atom.application.models.WebUser#lastName WebUser.lastName} field.
     * <p>
     * The <code>lastName</code> is subject to validation as it is mandatory for
     * transfers and has constraints imposed on the characters it can contain and
     * it's size.
     */
    @NotBlank(message = "User last name is mandatory and cannot contain only whitespace")
    @Size(min = 2, max = 30, message = "User last name must contain between 2 and 30 valid characters")
    private String lastName;

    /**
     * <b>The username of the web user</b>
     * <p>
     * For further details, see the definition of the
     * {@link com.atom.application.models.WebUser#username WebUser.username} field.
     * <p>
     * The <code>username</code> is subject to validation as it is mandatory for
     * transfers and has constraints imposed on the characters it can contain and
     * it's size.
     */
    @NotBlank(message = "User username is mandatory and cannot contain only whitespace")
    @Size(min = 5, max = 30, message = "User username must contain between 5 and 30 valid characters")
    private String username;

    /**
     * <b>The email address of the web user</b>
     * <p>
     * For further details, see the definition of the
     * {@link com.atom.application.models.WebUser#email WebUser.email} field.
     * <p>
     * The <code>email</code> is subject to validation as it is mandatory for
     * transfers and has constraints imposed on the characters it can contain and
     * it's size.
     */
    @NotBlank(message = "User email is mandatory and cannot contain only whitespace")
    @Size(min = 5, max = 70, message = "User email must contain between 5 and 70 valid characters")
    private String email;

    /**
     * <b>The password of the web user</b>
     * <p>
     * For further details, see the definition of the
     * {@link com.atom.application.models.WebUser#password WebUser.password} field.
     * <p>
     * The <code>password</code> is subject to validation as it is mandatory for
     * transfers and has constraints imposed on the characters it can contain and
     * it's size.
     * <p>
     * In the case of an input transfer (from client to server), the
     * <code>password</code> field stores the raw password. On the other hand, for
     * output transfers (from server to client), the field stores the encrypted
     * password.
     */
    @NotBlank(message = "User password is mandatory and cannot contain only whitespace")
    @Size(min = 8, max = 255, message = "User password must contain between 8 and 255 valid characters")
    private String password;

    /**
     * <b>The role of the web user</b>
     * <p>
     * For further details, see the definition of the
     * {@link com.atom.application.models.WebUser#role WebUser.role} field.
     * <p>
     * The <code>role</code> is subject to validation as it is mandatory for
     * transfers and has constraints imposed on the characters it can contain and
     * it's size.
     * <p>
     * The user role is stored as a <code>String</code> representing the role's
     * name, disregarding the role's ID, description and associated permissions.
     * <p>
     * The user role name should have the same validation constraints as the
     * {@link com.atom.application.models.UserRole#name UserRole.name} field.
     */
    @NotBlank(message = "User role name is mandatory and must not contain only whitespace")
    @Size(min = 4, max = 50, message = "User role name must contain between 4 and 50 characters")
    private String role;

    /**
     * <b>The locked status of the web user</b>
     * <p>
     * For further details, see the definition of the
     * {@link com.atom.application.models.WebUser#locked WebUser.locked} field.
     * <p>
     * The <code>locked</code> field is not subject to validation as it is not
     * required for a successful transfer, as well it being able to take only two
     * values (<code>true</code> or <code>false</code>).
     */
    private Boolean locked;

    /**
     * <b>The activated status of the web user</b>
     * <p>
     * For further details, see the definition of the
     * {@link com.atom.application.models.WebUser#activated WebUser.activated}
     * field.
     * <p>
     * The <code>activated</code> field is not subject to validation as it is not
     * required for a successful transfer, as well it being able to take only two
     * values (<code>true</code> or <code>false</code>).
     */
    private Boolean activated;

    private List<String> addresses;

    private List<String> phoneNumbers;

    /**
     * Retrieives the <code>id</code> of the web user.
     * <p>
     * For further details, see the definition of the {@link #id <code>id</code>}
     * field.
     * 
     * @return the <code>id</code> of the web user
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the <code>id</code> of the web user.
     * <p>
     * For further details, see the definition of the {@link #id <code>id</code>}
     * field.
     * 
     * @param id - the <code>id</code> of the web user
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieives the <code>firstName</code> of the web user.
     * <p>
     * For further details, see the definition of the {@link #firstName <code>firstName</code>}
     * field.
     * 
     * @return the <code>id</code> of the web user
     */
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

}
