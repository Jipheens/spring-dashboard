package com.dash.models;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String password;
    private String contact;
    private String gender;
    private String  dateOfBirth;
    private String pdfFile;

    // Implement UserDetails methods

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return the user's authorities/roles
        // You can customize this method to fetch and return the user's authorities from your data source
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Modify this according to your business logic
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Modify this according to your business logic
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Modify this according to your business logic
    }

    @Override
    public boolean isEnabled() {
        return true; // Modify this according to your business logic
    }
}
