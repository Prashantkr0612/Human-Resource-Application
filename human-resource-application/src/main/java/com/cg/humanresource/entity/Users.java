package com.cg.humanresource.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "pass_word", nullable = false)
    private String password;

    // Constructors
    public Users() {}

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // toString Method
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
