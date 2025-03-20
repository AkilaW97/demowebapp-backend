package com.ewis.demo.ewispc.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    /*
    * @Entity → Marks this as a JPA entity (linked to a database table).
    @Table(name = "users") → Specifies the table name in PostgreSQL.
    @Id and @GeneratedValue → Defines a primary key (id) that auto-increments.
    @Column(unique = true, nullable = false) → Ensures username is unique and required.
    @Enumerated(EnumType.STRING) → Stores user roles as ADMIN or USER.
    Lombok annotations (@Getter, @Setter, @Builder, etc.) simplify the code by generating getters, setters, and constructors.
    * */
}
