package org.example.tournamentplanner.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// @RestController = @Controller + @ResponseBody on every method
@RestController
public class TestController {

    // http://localhost:8080/api/greeting?name=John
    @GetMapping("/api/greeting")
    public GreetingDto greet(@RequestParam(defaultValue = "World") String name) {
        return new GreetingDto("What's up, %s!".formatted(name));
    }

    // Simple DTO used only for the response body
    public record GreetingDto(String message) {}
}