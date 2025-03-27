package com.ytu.sad.persistence.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ytu.sad.persistence.enums.CardTypeEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardEventDTO {

    private Integer cardId;
    @NotNull(message = "Match ID cannot be null")
    private Integer matchId;
    @NotNull(message = "Team ID cannot be null")
    private Integer teamId;
    private String teamName;
    @NotNull(message = "Player ID cannot be null")
    private Integer playerId;
    private String playerName;
    @NotNull(message = "Card Type cannot be null")
    private CardTypeEnum cardType;
    @NotNull(message = "Event Minute cannot be null")
    @Min(value = 1, message = "Minute must be greater than 0")
    private Integer minute;
}
