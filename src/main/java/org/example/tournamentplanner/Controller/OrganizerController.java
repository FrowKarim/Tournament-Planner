package org.example.tournamentplanner.Controller;

import org.example.tournamentplanner.Entities.Organizer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.example.tournamentplanner.DTO.OrganizerDTO;

import java.util.UUID;

@RestController
@RequestMapping("/Organizer")
public class OrganizerController {

    @GetMapping("/{id}")
    public UUID organizer(@RequestParam Organizer organizer) {

        return organizer.getId() ;
    }



}
