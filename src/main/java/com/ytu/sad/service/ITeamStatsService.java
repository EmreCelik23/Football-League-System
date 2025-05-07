package com.ytu.sad.service;

import com.ytu.sad.persistence.dto.TeamStatsDTO;

import java.util.List;

public interface ITeamStatsService {
    List<TeamStatsDTO> getAllTeamStats();

    TeamStatsDTO getTeamStatsByTeamId(Integer teamId);

    void deleteTeamStatsByTeamId(Integer teamId);

    void resetAllTeamStats();
}
