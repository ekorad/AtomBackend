package com.atom.application.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * <b>Entity representation of user roles</b>
 * <p>
 * User roles are basicaly named collections of user permissions and specify the
 * authority of a user. The authority determines the level at which the user can
 * interact with the application's database and its contents.
 * <p>
 * In simpler terms, the role of a user indicates whether they are, for example,
 * a normal user (a user having nearly no administrative rights) or an
 * administrator (a user with full administrative rights).
 * <p>
 * The actual rights provided by the user role depend on the internal collection
 * of associated user permissions.
 * 
 * @see {@link com.atom.application.models.UserPermission UserPermission}
 */
@Entity
@Table(name = "user_roles")
public class UserRole {

    /**
     * <b>Primary key of the user roles table</b>
     * <p>
     * Used to uniquely identify a single user role.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * <b>The name of the user role</b>
     * <p>
     * A more human-friendly label which concisely describes the level of authority
     * the role provides to a user.
     * <p>
     * Names should be written in a capitalized snake-case (e.g.
     * <code>MODERATOR</code>).
     * <p>
     * The name is also used to uniquely identify a single user role.
     */
    @Column(nullable = false, unique = true, length = 50)
    private String name;

    /**
     * <b>The description of the user role</b>
     * <p>
     * The description provides a short summary of what administrative rights does
     * the role bring to the user.
     * <p>
     * The description should also be unique, but for performance reasons it is not
     * implemented using the <code>unique</code> constraint.
     */
    @Column(nullable = false)
    private String description;

    /**
     * <b>The user permissions associated with the user role</b>
     * <p>
     * A collection of user permissions which actually specifies what administrative
     * rights does the role provide to a user.
     * <p>
     * There is an <i>M-to-N</i> relationship between user roles and user
     * permissions: a single user role can be associated with multiple user
     * permissions, and at the same time, a single user permission can also be
     * associated with multiple user roles.
     * <p>
     * The actual many-to-many relationship mapping is stored in a separate table
     * (<code>roles_permissions</code>) which maps role IDs to permission IDs.
     * <p>
     * As part of the entity implementation, the mapping is implemented using a
     * <code>Set</code>, as a single user permission can not be associated with the
     * same user role more than once.
     * <p>
     * <code>PERSIST</code> and <code>MERGE</code> cascading is used to ensure that
     * any new or altered permission included in the collection of associated
     * permissions will be automatically saved when the entire user role entity is
     * persisted.
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "roles_permissions", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
    private Set<UserPermission> permissions;

    /**
     * Retrieves the <code>id</code> of the user role.
     * <p>
     * For additional details, see the definition of the {@link #id <code>id</code>}
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
     * For additional details, see the definition of the {@link #id <code>id</code>}
     * field.
     * <p>
     * As IDs are automatically managed by Hibernate, this method should generally
     * be avoided, except for very specific cases.
     * 
     * @param id - the <code>id</code> of the user role
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the <code>name</code> of the user role.
     * <p>
     * For additional details, see the definition of the {@link #name
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
     * For additional details, see the definition of the {@link #name
     * <code>name</code>} field.
     * 
     * @param name - the <code>name</code> of the user role
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the <code>description</code> of the user role.
     * <p>
     * For additional details, see the definition of the {@link #description
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
     * For additional details, see the definition of the {@link #description
     * <code>description</code>} field.
     * 
     * @param description - the <code>description</code> of the user role
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the <code>permissions</code> associated with the user role.
     * <p>
     * For additional details, see the definition of the {@link #permissions
     * <code>permissions</code>} field.
     * 
     * @return the <code>permissions</code> associated with the user role
     */
    public Set<UserPermission> getPermissions() {
        return permissions;
    }

    /**
     * Sets the <code>permissions</code> associated with the user role.
     * <p>
     * For additional details, see the definition of the {@link #permissions
     * <code>permissions</code>} field.
     * 
     * @param permissions - the <code>permissions</code> associated with the user
     *                    role
     */
    public void setPermissions(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

}
