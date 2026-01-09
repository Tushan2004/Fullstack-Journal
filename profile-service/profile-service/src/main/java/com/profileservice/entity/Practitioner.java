package com.profileservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "practitioners")
public class Practitioner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID from user-service (foreign key, but no JPA relation)
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    private String firstName;
    private String lastName;

    // If you want a role for the practitioner, keep this enum
    @Enumerated(EnumType.STRING)
    private Role role;

    public Practitioner() {}

    public Practitioner(Long userId, String firstName, String lastName, Role role) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        if (role == null) throw new IllegalArgumentException("Role cannot be null");
        this.role = role;
    }

    @Override
    public String toString() {
        return "Practitioner{" +
                "id=" + id +
                ", userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role +
                '}';
    }
}