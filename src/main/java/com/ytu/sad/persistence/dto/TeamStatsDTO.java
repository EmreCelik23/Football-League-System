package com.ytu.sad.persistence.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamStatsDTO {

    private Integer id;

    @NotNull(message = "Team ID cannot be null")
    private Integer teamId;

    @NotNull(message = "Team Name cannot be null")
    private String teamName;

    @Min(value = 0, message = "Matches played cannot be negative")
    private Integer matchesPlayed;

    @Min(value = 0, message = "Points cannot be negative")
    private Integer points;

    @Min(value = 0, message = "Goals scored cannot be negative")
    private Integer goalsScored;

    @Min(value = 0, message = "Assists cannot be negative")
    private Integer assists;

    @Min(value = 0, message = "Yellow cards cannot be negative")
    private Integer yellowCards;

    @Min(value = 0, message = "Red cards cannot be negative")
    private Integer redCards;

    @Min(value = 0, message = "Goals conceded cannot be negative")
    private Integer goalsConceded;

    @Min(value = 0, message = "Clean sheets cannot be negative")
    private Integer cleanSheets;

    private Double pointsPerMatch;
    private Double goalsPerMatch;
    private Double assistsPerMatch;
    private Double goalsConcededPerMatch;
    private Double yellowCardsPerMatch;
    private Double redCardsPerMatch;
}