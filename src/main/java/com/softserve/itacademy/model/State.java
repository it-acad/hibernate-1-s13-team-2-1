package com.softserve.itacademy.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "states")
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9\\-\\_\\ ]{1,20}$")
    private String name;

    @OneToMany(mappedBy = "state")
    private List<Task> tasks;

}
