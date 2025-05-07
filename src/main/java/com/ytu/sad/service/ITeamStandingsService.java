package com.ytu.sad.service;

import com.ytu.sad.persistence.dto.TeamStandingsDTO;

import java.util.List;

public interface ITeamStandingsService {
    List<TeamStandingsDTO> getAllTeamStandings();

    TeamStandingsDTO getTeamStandingsByTeamId(Integer teamId);

    void deleteTeamStandingsByTeamId(Integer teamId);

    void resetAllTeamStandings();
}
