package com.ytu.sad.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LineupsDTO {
    private Integer matchId;
    private Integer homeTeamId;
    private List<Integer> homeTeamStartingIDs;
    private List<Integer> homeTeamReservesIDs;
    private Integer awayTeamId;
    private List<Integer> awayTeamStartingIDs;
    private List<Integer> awayTeamReservesIDs;
}
