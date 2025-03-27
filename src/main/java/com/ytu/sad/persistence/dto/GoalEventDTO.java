package com.ytu.sad.persistence.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoalEventDTO {

    private Integer goalId;
    @NotNull(message = "Match ID cannot be null")
    private Integer matchId;
    @NotNull(message = "Team ID cannot be null")
    private Integer teamId;
    private String teamName;
    @NotNull(message = "Player ID cannot be null")
    private Integer playerId;
    private String playerName;
    private Integer assistPlayerId;
    private String assistPlayerName;
    @NotNull(message = "Event minute cannot be null")
    @Min(value = 1, message = "Minute must be greater than 0")
    private Integer minute;
}
