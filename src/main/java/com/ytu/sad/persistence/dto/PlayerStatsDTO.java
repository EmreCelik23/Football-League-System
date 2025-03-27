package com.ytu.sad.persistence.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerStatsDTO {
    private Integer id;

    @NotNull(message = "Player ID cannot be null")
    private Integer playerId;

    private String playerName;

    @Min(value = 0, message = "Matches Played cannot be negative")
    private Integer matchesPlayed;

    @Min(value = 0, message = "Goals cannot be negative")
    private Integer goals;

    @Min(value = 0, message = "Assists cannot be negative")
    private Integer assists;

    @Min(value = 0, message = "Yellow Cards cannot be negative")
    private Integer yellowCards;

    @Min(value = 0, message = "Red Cards cannot be negative")
    private Integer redCards;

    @Min(value = 0, message = "Minutes Played cannot be negative")
    private Integer minutesPlayed;

    private Double goalsPerMatch;
    private Double assistsPerMatch;
}