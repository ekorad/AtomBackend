package com.atom.application.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <b>Entity representation of a user role</b>
 * <p>
 * User permissions determine to what degree can a user interact with the
 * application's database and its contents.
 * <p>
 * In simpler terms, permissions determine whether a user can, for example,
 * delete the comment of another user, or edit the account of another user.
 */
@Entity
@Table(name = "user_permissions")
public class UserPermission {

    /**
     * <b>Primary key of the user permissions table</b>
     * <p>
     * Used to uniquely identify a single user permission.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * <b>The name of the user permission</b>
     * <p>
     * A more human-friendly label which concisely describes the access rights the
     * permission provides to a user
     * <p>
     * Names should be written in a capitalized snake-case (e.g.
     * <code>READ_ANY_ROLE</code>).
     * <p>
     * The name is also used to uniquely identify a single user permission.
     */
    @Column(nullable = false, unique = true, length = 50)
    private String name;

    /**
     * <b>The description of the user permission</b>
     * <p>
     * The description provides a short summary of what access rights does the
     * permission provide to a user.
     * <p>
     * The description should also be unique, but for performance reasons it is not
     * implemented using the <code>unique</code> constraint.
     */
    @Column(nullable = false)
    private String description;

    /**
     * Retrieves the <code>id</code> of the user permission.
     * <p>
     * For further details, see the definition of the {@link #id <code>id</code>} field.
     * 
     * @return the <code>id</code> of the user permission
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the <code>id</code> of the user permission.
     * <p>
     * For further details, see the definition of the {@link #id <code>id</code>} field.
     * <p>
     * As IDs are automatically managed by Hibernate, this method should generally
     * be avoided, except for very specific cases.
     * 
     * @param id - the <code>id</code> of the user permission
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the <code>name</code> of the user permission.
     * <p>
     * For further details, see the definition of the {@link #name <code>name</code>} field.
     * 
     * @return the <code>name</code> of the user permission
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the <code>name</code> of the user permission.
     * <p>
     * For further details, see the definition of the {@link #name <code>name</code>} field.
     * 
     * @param name - the <code>name</code> of the user permission
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the <code>description</code> of the user permission.
     * <p>
     * For further details, see the definition of the {@link #description <code>description</code>} field.
     * 
     * @return the <code>description</code> of the user permission
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the <code>description</code> of the user permission.
     * <p>
     * For further details, see the definition of the {@link #description <code>description</code>} field.
     * 
     * @param description - the <code>description</code> of the user permission
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
