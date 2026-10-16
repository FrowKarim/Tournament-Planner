package org.example.tournamentplanner.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "team_captains")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TeamCaptain {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false, unique = true)
    private Player player;

    // optional extra field
    private String phone;
}