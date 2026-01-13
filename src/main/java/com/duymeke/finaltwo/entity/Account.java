package com.duymeke.finaltwo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class Account implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<WorkItem> tasks;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "accounts_roles",
            joinColumns = @JoinColumn(name = "account_model_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id")
    )
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
