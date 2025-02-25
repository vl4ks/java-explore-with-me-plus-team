package ru.practicum.user.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;
}
