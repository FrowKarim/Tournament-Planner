package org.example.tournamentplanner.Entities;

import java.time.LocalDate;

public class Tournament
{
    public String ID;
    public String name;
    public String bracketType;
    public String gameFormat;
    public String description;
    public int capacity;
    public LocalDate startTime;

    public Tournament(String ID, String name, String bracketType, String gameFormat, int capacity, LocalDate startTime) {
        this.ID = ID;
        this.name = name;
        this.bracketType = bracketType;
        this.gameFormat = gameFormat;
        this.capacity = capacity;
        this.startTime = startTime;
    }
}
