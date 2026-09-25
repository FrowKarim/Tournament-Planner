package org.example.tournamentplanner.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Plain POJO that Jackson will (de)serialise to JSON.
 * Lombok saves us from writing getters/setters/constructors.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestDTO {
    private Long id;      // will be set by the service when a POST occurs
    private String name;  // arbitrary payload field
}