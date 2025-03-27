package com.ytu.sad.persistence.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamStandingsDTO {
    private Integer id;

    @NotNull(message = "Team ID cannot be null")
    private Integer teamId;

    @NotNull(message = "Team Name cannot be null")
    private String teamName;

    @Min(value = 0, message = "Points cannot be negative")
    private Integer points;

    @Min(value = 0, message = "Matches Played cannot be negative")
    private Integer matchesPlayed;

    @Min(value = 0, message = "Wins cannot be negative")
    private Integer wins;

    @Min(value = 0, message = "Draws cannot be negative")
    private Integer draws;

    @Min(value = 0, message = "Losses cannot be negative")
    private Integer losses;

    @Min(value = 0, message = "Goals Scored cannot be negative")
    private Integer goalsScored;

    @Min(value = 0, message = "Goals Conceded cannot be negative")
    private Integer goalsConceded;

    private Integer goalDifference;
}