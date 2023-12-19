package com.cs790.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    private String id;
    private String name;

    private String email;

    private String password;

    private String phone;

    @Transient
    private String rawPassword; // Temporary field to store the raw (unencrypted) password

    private String encryptedPassword;


    // Add a method to set and encrypt the password
    public void setAndEncryptPassword(String rawPassword) {
        this.rawPassword = rawPassword;
//        this.encryptedPassword = new BCryptPasswordEncoder().encode(rawPassword);
    }


}
