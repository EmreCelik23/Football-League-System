package com.ytu.sad.service;

import com.ytu.sad.persistence.dto.MatchDTO;
import com.ytu.sad.persistence.dto.PlayerDTO;
import com.ytu.sad.persistence.dto.TeamDTO;

import java.util.List;

public interface ITeamService {
    List<TeamDTO> getAllTeams();

    TeamDTO getTeamById(Integer teamId);

    List<PlayerDTO> getPlayersByTeam(Integer teamId);

    TeamDTO addTeam(TeamDTO teamDTO);

    void deleteTeam(Integer teamId);

    TeamDTO updateTeam(Integer teamId, TeamDTO teamDTO);

    List<MatchDTO> getMatchesByTeam(Integer teamId);
}
