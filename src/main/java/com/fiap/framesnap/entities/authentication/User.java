package com.fiap.framesnap.entities.authentication;

import java.util.UUID;

public class User {
    private UUID id;
    private String email;
    private String password;

    public User(UUID id, String email, String password) {
        if (email == null) {
            throw new IllegalArgumentException("Email não pode ser nulo");
        }

        if (password == null) {
            throw new IllegalArgumentException("Senha não pode ser nula");
        }

        this.id = id;
        this.email = email;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}