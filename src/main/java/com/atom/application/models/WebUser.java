package com.atom.application.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <b>Entity representation of a (web) user</b>
 * <p>
 * The <code>WebUser</code> is basically an implementation of a user account as
 * an entity, which can be saved in the application's database.
 * <p>
 * The user account encompasses basic but sensitive user information, such as
 * the user's actual name, their e-mail address or their password.
 * <p>
 * As the user's password represents very sensitive data, it is stored securely
 * within the database using modern encryption algorithms.
 */
@Entity
@Table(name = "users")
public class WebUser {

    /**
     * <b>The password encoder</b>
     * <p>
     * Used for securely encrypting a raw password.
     * <p>
     * In this case, the BCRYPT algorithm is used for encryption.
     */
    private static final PasswordEncoder PWD_ENCODER = new BCryptPasswordEncoder();

    /**
     * <b>Primary key of the users table</b>
     * <p>
     * Used to uniquely identify a single user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * <b>The actual first name of the user</b>
     */
    @Column(nullable = false, length = 30)
    private String firstName;

    /**
     * <b>The actual last name of the user</b>
     */
    @Column(nullable = false, length = 30)
    private String lastName;

    /**
     * <b>The username of the user's account</b>
     * <p>
     * The username is what is being displayed to other users and acts as a public
     * identifier for a specific user.
     * <p>
     * It is also required in order to log in on the application web site.
     * <p>
     * The username also acts as a more human-friendly unique identifier of a single
     * user.
     */
    @Column(nullable = false, length = 30, unique = true)
    private String username;

    /**
     * <b>The actual e-mail address of the user</b>
     * <p>
     * The stored e-mail address must respect the correct format as it is the
     * primary means of contacting the user.
     * <p>
     * It can also be used to uniquely identify a single user.
     */
    @Column(nullable = false, length = 70, unique = true)
    private String email;

    /**
     * <b>The encrypted password of the user</b>
     * <p>
     * The password is guaranteed to <b>NOT</b> be stored in cleartext and is
     * instead encrypted using a modern and apporved encryption algorithm. For
     * further details, see the definition of the {@link #setPassword(String)
     * <code>setPassword()</code>} function.
     * <p>
     * Along with the {@link #username}, the password is also required in order to
     * be able to log in on the application web site.
     */
    @Column(nullable = false)
    private String password;

    /**
     * <b>The locked status of the user's account</b>
     * <p>
     * Specifies whether or not the user account is locked.
     * <p>
     * A user with a locked account cannot log in on the application web site and
     * must ask for its account to be unlocked prior to any other log in attempts.
     * <p>
     * A user's account can only get locked if they (or someone else) attempt to log
     * in multiple times with incorrect credentials and in a short amount of time.
     * <p>
     * After registration, the user's account is initially unlocked.
     */
    @Column(nullable = false)
    private Boolean locked = false;

    /**
     * <b>The activation status of the user's account</b>
     * <p>
     * A user account which is not activated cannot place orders or post
     * commentaries or reviews of products.
     * <p>
     * Activation is achieved through an activation link sent either when the
     * account is created or at a later time at the user's explicit request.
     * <p>
     * After registration, the user's account is initially not activated.
     */
    @Column(nullable = false)
    private Boolean activated = false;

    @ElementCollection
    @CollectionTable(name = "user_adresses", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "address")
    private List<String> addresses = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "user_phone_numbers", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "phone_number")
    private List<String> phoneNumbers = new ArrayList<>();

    /**
     * <b>The user role associated with the user's account</b>
     * <p>
     * The role of a user specifies the user's level of authority, or more
     * precisely, what administrative rights does the user posses in order to
     * interact with the application's database and it's contents.
     * <p>
     * There is an <i>M-to-1</i> relationship between users and user roles: a single
     * user can only have one role, while a single role can be associated with
     * multiple users.
     * <p>
     * The actual many-to-one relationship is implemented within the same table, in
     * a separate column which stores the actual ID of the associated user role.
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "role", nullable = false)
    private UserRole role;

    /**
     * Retrieves the <code>id</code> of the user.
     * <p>
     * For additional details, see the definition of the {@link #id <code>id</code>}
     * field.
     * 
     * @return the <code>id</code> of the user
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the <code>id</code> of the user.
     * <p>
     * For additional details, see the definition of the {@link #id <code>id</code>}
     * field.
     * <p>
     * As IDs are automatically managed by Hibernate, this method should generally
     * be avoided, except for very specific cases.
     * 
     * @param id - the <code>id</code> of the user
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the <code>firstName</code> of the user.
     * <p>
     * For additional details, see the definition of the {@link #firstName
     * <code>firstName</code>} field.
     * 
     * @return the <code>firstName</code> of the user
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the <code>firstName</code> of the user.
     * <p>
     * For additional details, see the definition of the {@link #firstName
     * <code>firstName</code>} field.
     * 
     * @param firstName - the <code>firstName</code> of the user
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retrieves the <code>lastName</code> of the user.
     * <p>
     * For additional details, see the definition of the {@link #lastName
     * <code>lastName</code>} field.
     * 
     * @return the <code>lastName</code> of the user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the <code>lastName</code> of the user.
     * <p>
     * For additional details, see the definition of the {@link #lastName
     * <code>lastName</code>} field.
     * 
     * @param lastName - the <code>lastName</code> of the user
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retrieves the <code>username</code> of the user.
     * <p>
     * For additional details, see the definition of the {@link #username
     * <code>username</code>} field.
     * 
     * @return the <code>username</code> of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the <code>username</code> of the user.
     * <p>
     * For additional details, see the definition of the {@link #username
     * <code>username</code>} field.
     * 
     * @param username - the <code>username</code> of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retrieves the <code>email</code> of the user.
     * <p>
     * For additional details, see the definition of the {@link #email
     * <code>email</code>} field.
     * 
     * @return the <code>email</code> of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the <code>email</code> of the user.
     * <p>
     * For additional details, see the definition of the {@link #email
     * <code>email</code>} field.
     * 
     * @param email - the <code>email</code> of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the <code>password</code> of the user.
     * <p>
     * For additional details, see the definition of the {@link #password
     * <code>password</code>} field.
     * <p>
     * As the password is not stored in its raw form, what will actually be returned
     * is its encrypted version. For further details, see the definition of the
     * {@link #setPassword(String) <code>setPassword</code>} function.
     * 
     * @return the <code>password</code> of the user (the encryption of the raw
     *         password)
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the <code>password</code> of the user.
     * <p>
     * For additional details, see the definition of the {@link #password
     * <code>password</code>} field.
     * <p>
     * The function is guaranteed to <b>NOT</b> store the password in raw form. As
     * such, it uses the local password encoder ({@link #PWD_ENCODER
     * <code>PWD_ENCODER</code>}) to store the encryption of the raw password
     * instead.
     * 
     * @param password - the raw password
     */
    public void setPassword(String password) {
        this.password = PWD_ENCODER.encode(password);
    }

    /**
     * Retrieves the <code>locked</code> status of the user's account.
     * <p>
     * For additional details, see the definition of the {@link #locked
     * <code>locked</code>} field.
     * 
     * @return the <code>locked</code> status of the user's account
     */
    public Boolean getLocked() {
        return locked;
    }

    /**
     * Sets the <code>locked</code> status of the users's account.
     * <p>
     * For additional details, see the definition of the {@link #locked
     * <code>locked</code>} field.
     * 
     * @param locked - the <code>locked</code> status of the users's account
     */
    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    /**
     * Retrieves the <code>activated</code> status of the user's account.
     * <p>
     * For additional details, see the definition of the {@link #activated
     * <code>activated</code>} field.
     * 
     * @return the <code>activated</code> status of the user's account
     */
    public Boolean getActivated() {
        return activated;
    }

    /**
     * Sets the <code>activated</code> status of the users's account.
     * <p>
     * For additional details, see the definition of the {@link #activated
     * <code>activated</code>} field.
     * 
     * @param activated - the <code>activated</code> status of the users's account
     */
    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    /**
     * Retrieves the <code>role</code> of the user.
     * <p>
     * For additional details, see the definition of the {@link #role
     * <code>role</code>} field.
     * 
     * @return the <code>role</code> of the user
     */
    public UserRole getRole() {
        return role;
    }

    /**
     * Sets the <code>role</code> of the user.
     * <p>
     * For additional details, see the definition of the {@link #role
     * <code>role</code>} field.
     * 
     * @param role - the <code>role</code> status of the users's account
     */
    public void setRole(UserRole role) {
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

    @Override
    public String toString() {
        return "WebUser [activated=" + activated + ", addresses=" + addresses + ", email=" + email + ", firstName="
                + firstName + ", id=" + id + ", lastName=" + lastName + ", locked=" + locked + ", password=" + password
                + ", role=" + role + ", username=" + username + "]";
    }


    
}
