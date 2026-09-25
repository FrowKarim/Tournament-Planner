package org.example.tournamentplanner;

import org.springframework.boot.SpringApplication;

public class TestTournamentPlannerApplication {

    public static void main(String[] args) {
        SpringApplication.from(TournamentPlannerApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
