package com.atom.application.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * <b>User permission DTO</b>
 * <p>
 * This is a DTO, which means that it stores a minimal set of properties from
 * the corresponding entity type, making it easier to transfer over HTTP.
 * <p>
 * As user permissions are read-only, DTO validation is kept purely for
 * consistency, and has no real use.
 * <p>
 * This DTO stores all the data from the user permission entity
 * (<code>UserPermission</code>).
 * <p>
 * The mapping operation is performed using a mapper object
 * (<code>UserPermissionMapper</code>).
 * 
 * @see {@link com.atom.application.models.UserPermission UserPermission}
 * @see {@link com.atom.application.mappers.UserPermissionMapper
 *      UserPermissionMapper}
 */
public class UserPermissionDTO {

    /**
     * <b>The ID of the user permission</b>
     * <p>
     * For further details, see the definition of the
     * {@link com.atom.application.models.UserPermission#id UserPermission.id}
     * field.
     * <p>
     * The <code>id</code> is mainly used for output purposes (from server to
     * client), as entity IDs are maintained automatically by Hibernate.
     * <p>
     * The <code>id</code> is not subject to validation as it is not required for a
     * successful transfer, as well as being able to take any numerical value.
     */
    private Long id;

    /**
     * <b>The name of the user permission</b>
     * <p>
     * For further details, see the definition of the
     * {@link com.atom.application.models.UserPermission#name UserPermission.name}
     * field.
     */
    @NotBlank(message = "User permission name is mandatory and cannot contain only whitespace")
    @Size(min = 5, max = 50, message = "User permission name must contain between 5 and 50 valid characters")
    private String name;

    /**
     * <b>The description of the user permission</b>
     * <p>
     * For further details, see the definition of the
     * {@link com.atom.application.models.UserPermission#description
     * UserPermission.description} field.
     */
    @NotBlank(message = "User permission description is mandatory and cannot contain only whitespace")
    @Size(min = 5, max = 255, message = "User permission description must contain between 5 and 255 valid characters")
    private String description;

    /**
     * Retrieves the <code>id</code> of the user permission.
     * <p>
     * For further details, see the definition of the {@link #id <code>id</code>}
     * field.
     * 
     * @return the <code>id</code> of the user permission
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the <code>id</code> of the user permission.
     * <p>
     * For further details, see the definition of the {@link #id <code>id</code>}
     * field.
     * 
     * @param id - the <code>id</code> of the user permission
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the <code>name</code> of the user permission.
     * <p>
     * For further details, see the definition of the {@link #name
     * <code>name</code>} field.
     * 
     * @return the <code>name</code> of the user permission
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the <code>name</code> of the user permission.
     * <p>
     * For further details, see the definition of the {@link #name
     * <code>name</code>} field.
     * 
     * @param name - the <code>name</code> of the user permission
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the <code>description</code> of the user permission.
     * <p>
     * For further details, see the definition of the {@link #description
     * <code>description</code>} field.
     * 
     * @return the <code>description</code> of the user permission
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the <code>description</code> of the user permission.
     * <p>
     * For further details, see the definition of the {@link #description
     * <code>description</code>} field.
     * 
     * @param description - the <code>description</code> of the user permission
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
