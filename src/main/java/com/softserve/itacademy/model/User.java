package com.softserve.itacademy.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User  {

    // life is too short for long names {1,20}
    private static final String NAME_PATTERN = "([A-Z][a-z]{1,20})(\\-(?!(?i)\\1)[A-Z][a-z]{1,20}){0,1}";
    // required: one lower, upper, digit and spec char
    private static final String PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    @NotBlank
    @Email
    private String email;

    @Column
    @NotBlank
    @Pattern(regexp = NAME_PATTERN)
    private String firstName;

    @Column
    @NotBlank
    @Pattern(regexp = NAME_PATTERN)
    private String lastName;

    @Column
    @NotBlank
    @Pattern(regexp = PASSWORD_PATTERN)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "owner")
    private List<ToDo> toDos;

    @ManyToMany(mappedBy = "collaborators")
    private List<ToDo> collaboratedToDos;
}