package com.atom.application.dtos;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * <b>User role DTO</b>
 * <p>
 * This is a DTO, which means that it stores a minimal set of properties from
 * the corresponding entity type, making it easier to transfer over HTTP.
 * <p>
 * As DTOs are objects used for transferring data between server and client,
 * validation is mandatory.
 * <p>
 * This DTO stores all the data from the user role entity
 * (<code>UserRole</code>). A special case are the associated permissions, which
 * are stored only by their names.
 * <p>
 * The mapping operation is performed using a mapper object
 * (<code>UserRoleMapper</code>).
 * 
 * @see {@link com.atom.application.models.UserRole UserRole}
 * @see {@link com.atom.application.mappers.UserRoleMapper UserRoleMapper}
 */
public class UserRoleDTO {

    /**
     * <b>The ID of the user role</b>
     * <p>
     * For further details, see the definition of the
     * {@link com.atom.application.models.UserRole#id UserPermission.id} field.
     * <p>
     * The <code>id</code> is mainly used for output purposes (from server to
     * client), as entity IDs are maintained automatically by Hibernate.
     * <p>
     * The <code>id</code> is not subject to validation as it is not required for a
     * successful transfer, as well as being able to take any numerical value.
     */
    private Long id;

    /**
     * <b>The name of the user role</b>
     * <p>
     * For further details, see the definition of the
     * {@link com.atom.application.models.UserRole#name UserRole.name} field.
     * <p>
     * The <code>name</code> is subject to validation as it is mandatory for
     * transfers and has constraints imposed on the characters it can contain and
     * it's size.
     */
    @NotBlank(message = "User role name is mandatory and must not contain only whitespace")
    @Size(min = 4, max = 50, message = "User role name must contain between 4 and 50 characters")
    private String name;

    /**
     * <b>The description of the user role</b>
     * <p>
     * For further details, see the definition of the
     * {@link com.atom.application.models.UserRole#description UserRole.description}
     * field.
     * <p>
     * The <code>description</code> is subject to validation as it is mandatory for
     * transfers and has constraints imposed on the characters it can contain and
     * it's size.
     */
    @NotBlank(message = "User role description is mandatory and must not contain only whitespace")
    @Size(min = 5, max = 255, message = "User role description must contain between 5 and 255 characters")
    private String description;

    /**
     * <b>The user permissions associated with the user role</b>
     * <p>
     * For further details, see the definition of the
     * {@link com.atom.application.models.UserRole#permissions UserRole.permissions}
     * field.
     * <p>
     * The user permissions are stored as a <code>Set</code> of permission names,
     * disregarding their IDs and names.
     * <p>
     * The associated user permission names are subject for validation as they are
     * mandatory for transfers and have constraints imposed on the characters they
     * can contain and their sizes. As such, the container for the permission names
     * also requires validation as it should never be empty. The user permission
     * names should have the same validation constraints as the
     * {@link com.atom.application.models.UserPermission#name <code>name</code>}
     * field.
     */
    @NotEmpty(message = "Associated permission list is mandatory and must not be empty")
    private Set<@NotBlank(message = "User permission name is mandatory and cannot contain only whitespace") @Size(min = 5, max = 50, message = "User permission name must contain between 5 and 50 valid characters") String> permissions;

    /**
     * Retrieives the <code>name</code> of the user role.
     * <p>
     * For further details, see the definition of the {@link #name
     * <code>name</code>} field.
     * 
     * @return the <code>name</code> of the user role
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the <code>name</code> of the user role.
     * <p>
     * For further details, see the definition of the {@link #name
     * <code>name</code>} field.
     * 
     * @param name - the <code>name</code> of the user role
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieives the <code>description</code> of the user role.
     * <p>
     * For further details, see the definition of the {@link #description
     * <code>description</code>} field.
     * 
     * @return the <code>description</code> of the user role
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the <code>description</code> of the user role.
     * <p>
     * For further details, see the definition of the {@link #description
     * <code>description</code>} field.
     * 
     * @param description - the <code>description</code> of the user role
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieives the <code>id</code> of the user role.
     * <p>
     * For further details, see the definition of the {@link #id <code>id</code>}
     * field.
     * 
     * @return the <code>id</code> of the user role
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the <code>id</code> of the user role.
     * <p>
     * For further details, see the definition of the {@link #id <code>id</code>}
     * field.
     * 
     * @param id - the <code>id</code> of the user role
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieives the <code>permissions</code> associated with the user role.
     * <p>
     * For further details, see the definition of the {@link #permissions
     * <code>permissions</code>} field.
     * 
     * @return the <code>permissions</code> associated with the user role
     */
    public Set<String> getPermissions() {
        return permissions;
    }

    /**
     * Sets the <code>permissions</code> associated with the user role.
     * <p>
     * For further details, see the definition of the {@link #permissions
     * <code>permissions</code>} field.
     * 
     * @param permissions - the <code>permissions</code> associated with the user
     *                    role
     */
    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

}
