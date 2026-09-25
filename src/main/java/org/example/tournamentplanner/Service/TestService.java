package org.example.tournamentplanner.Service;

import org.example.tournamentplanner.DTO.TestDTO;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Very small in‑memory store.
 * In a real application you would inject a JPA repository here.
 */
@Service
public class TestService {

    private final Map<Long, TestDTO> store = new HashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    /** Return all stored DTOs */
    public List<TestDTO> findAll() {
        return new ArrayList<>(store.values());
    }

    /** Save a new DTO – the id is auto‑generated */
    public TestDTO save(TestDTO incoming) {
        long id = nextId.getAndIncrement();
        TestDTO saved = new TestDTO(id, incoming.getName());
        store.put(id, saved);
        return saved;
    }
}