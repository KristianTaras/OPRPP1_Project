package hr.algebra.model.entities;

import hr.algebra.model.interfaces.Column;

public class User extends BaseEntity {

    @Column(name = "first_name")
    private final String firstName;
    @Column(name = "last_name")
    private final String lastName;
    private final String email;
    @Column(name = "phone_number")
    private final String phoneNumber;
    private final String username;
    @Column(name = "password_hash")
    private final String passwordHash;
    private final Role role;


    public User(int id,
                String firstName,
                String lastName,
                String email,
                String phoneNumber,
                String username,
                String passwordHash,
                Role role) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public Role getRole() { return role; }

    @Override
    public boolean equals(Object o) {
        return o instanceof User that && super.equals(that);
    }

    @Override
    public int hashCode() { return super.hashCode(); }
}
