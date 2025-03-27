package com.ytu.sad.persistence.dto;

import com.ytu.sad.persistence.entity.SubstitutionEvent;
import com.ytu.sad.persistence.enums.MatchStatusEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class MatchDTO {
    private Integer id;
    @NotNull(message = "Home Team ID cannot be null")
    private Integer homeTeamId;
    private String homeTeamName;
    @NotNull(message = "Away Team ID cannot be null")
    private Integer awayTeamId;
    private String awayTeamName;
    private String referee;
    @NotNull(message = "Match Date cannot be null")
    private LocalDateTime date;
    private String stadium;
    @Min(value = 0, message = "Score cannot be negative")
    private Integer homeScore;
    @Min(value = 0, message = "Score cannot be negative")
    private Integer awayScore;
    private MatchStatusEnum status;
    private List<GoalEventDTO> goals;
    private List<CardEventDTO> yellowCards;
    private List<CardEventDTO> redCards;
    private List<SubstitutionEventDTO> substitutions;
}
