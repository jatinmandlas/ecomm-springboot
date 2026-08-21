package com.Ecommerce.ecomm_springboot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users",uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;
    @NotBlank
    @Size(max = 20)
    @Column(name="username")
    private String userName;
    @NotBlank
    @Size(max = 120)
    private String password;
    @NotBlank
    @Size(max = 50)
    @Email
    @Column(name="email")
    private String email;

    public User(String email, String password, String userName) {
        this.email = email;
        this.password = password;
        this.userName = userName;
    }


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}
            , fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")

    )
    @Getter
    @Setter
    private Set<Roles> roles = new HashSet<>();

    @ManyToMany(cascade =  {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="user_addresses",
    joinColumns = @JoinColumn(name="user_id"),
    inverseJoinColumns = @JoinColumn(name="address_id"))
    private List<Address> addresses=new ArrayList<>();
    @Getter
    @Setter
    @ToString.Exclude
    @OneToMany(mappedBy ="user",cascade = {CascadeType.MERGE,CascadeType.PERSIST},orphanRemoval = true)
    private Set<Product> products;



}

