package org.example.tournamentplanner.Controller;

import org.example.tournamentplanner.DTO.TestDTO;
import org.example.tournamentplanner.Service.TestService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller exposing:
 *   GET  /api/test   → list all TestDTO objects
 *   POST /api/test   → create a new TestDTO (JSON in request body)
 */
@RestController
@RequestMapping("/api/test")   // base path for all methods in this class
public class TestController2 {

    private final TestService testService;

    // Constructor injection (recommended over field injection)
    public TestController2(TestService testService) {
        this.testService = testService;
    }

    /** GET /api/test */
    @GetMapping
    public List<TestDTO> getAll() {
        return testService.findAll();
    }

    /** POST /api/test */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201 Created is the conventional response
    public TestDTO create(@RequestBody TestDTO dto) {
        // Spring automatically deserialises JSON → TestDTO
        return testService.save(dto);
    }
}