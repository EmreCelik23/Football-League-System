package com.ytu.sad.persistence.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubstitutionEventDTO {

    private Integer substitutionId;

    @NotNull(message = "Match ID cannot be null")
    private Integer matchId;

    @NotNull(message = "Team ID cannot be null")
    private Integer teamId;

    @NotNull(message = "Out player ID cannot be null")
    private Integer outPlayerId;

    private String outPlayerName;

    @NotNull(message = "In player ID cannot be null")
    private Integer inPlayerId;

    private String inPlayerName;

    @NotNull(message = "Substitution minute cannot be null")
    @Min(value = 1, message = "Minute must be greater than 0")
    @Max(value = 90, message = "Minute must be at most 120")
    private Integer minute;
}