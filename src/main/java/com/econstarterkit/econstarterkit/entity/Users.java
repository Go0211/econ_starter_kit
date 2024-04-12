package com.econstarterkit.econstarterkit.entity;

import com.econstarterkit.econstarterkit.type.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Users{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "email", unique = true)
    String email;

    @Column(name = "password")
    String password;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    UserRole userRole;

}
