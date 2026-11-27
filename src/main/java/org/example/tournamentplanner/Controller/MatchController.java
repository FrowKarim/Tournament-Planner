package org.example.tournamentplanner.Controller;
import org.example.tournamentplanner.Entities.Match;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/match")
public class MatchController {

    @GetMapping
    public Match getMatch(@RequestParam Long matchID) {

        return  getMatch(matchID);
    }
    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public String processMessage(String message) {
        return "Received: " + message;
    }

}


