package org.example.tournamentplanner.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "players")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    // bi‑directional many‑to‑many with Team
    @Builder.Default
    @ManyToMany(mappedBy = "players")
    @JsonIgnore
    private List<Team> teams = new ArrayList<>();
}