package org.example.tournamentplanner.DTO;

import jakarta.persistence.*;
import lombok.*;
import org.example.tournamentplanner.Entities.Team;

import java.util.*;

public record PlayerDTO (Long id,String firstName,String lastName,String email,Set<Team> teams ){ }