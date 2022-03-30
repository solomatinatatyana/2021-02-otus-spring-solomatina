package ru.otus.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "library_users")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY  )
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "active")
    private boolean active;

    //@ElementCollection(targetClass = Role.class,fetch = FetchType.EAGER)
    //@CollectionTable(name = "authorities", joinColumns = @JoinColumn(name = "username"))
   /* @Enumerated(EnumType.STRING)
    private Set<Role> roles;*/
    @Enumerated(EnumType.STRING)
    @Column(name = "roles")
    private Role roles;
}
