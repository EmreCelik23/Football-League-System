package com.ytu.sad.service.impl;

import com.ytu.sad.exception.ResourceNotFoundException;
import com.ytu.sad.persistence.dto.MatchDTO;
import com.ytu.sad.persistence.dto.PlayerDTO;
import com.ytu.sad.persistence.dto.TeamDTO;
import com.ytu.sad.persistence.entity.Team;
import com.ytu.sad.persistence.entity.TeamStandings;
import com.ytu.sad.persistence.entity.TeamStats;
import com.ytu.sad.persistence.mapper.MatchMapper;
import com.ytu.sad.persistence.mapper.PlayerMapper;
import com.ytu.sad.persistence.mapper.TeamMapper;
import com.ytu.sad.persistence.repository.TeamRepository;
import com.ytu.sad.persistence.repository.TeamStandingsRepository;
import com.ytu.sad.persistence.repository.TeamStatsRepository;
import com.ytu.sad.service.ITeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl implements ITeamService {

    private final PlayerMapper playerMapper;

    private final TeamMapper teamMapper;

    private final MatchMapper matchMapper;

    private final TeamRepository teamRepository;
    private final TeamStatsRepository teamStatsRepository;
    private final TeamStandingsRepository teamStandingsRepository;

    @Autowired
    public TeamServiceImpl(PlayerMapper playerMapper, TeamMapper teamMapper, MatchMapper matchMapper, TeamRepository teamRepository, TeamStatsRepository teamStatsRepository, TeamStandingsRepository teamStandingsRepository) {
        this.playerMapper = playerMapper;
        this.teamMapper = teamMapper;
        this.matchMapper = matchMapper;
        this.teamRepository = teamRepository;
        this.teamStatsRepository = teamStatsRepository;
        this.teamStandingsRepository = teamStandingsRepository;
    }

    @Override
    public List<TeamDTO> getAllTeams() {
        List<Team> teams = teamRepository.findAll();
        return teamMapper.toTeamDtoList(teams);
    }

    @Override
    public TeamDTO getTeamById(Integer teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team with ID:" + teamId + " is not exist. "));
        return teamMapper.toTeamDto(team);
    }

    @Override
    public List<PlayerDTO> getPlayersByTeam(Integer teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team with ID:" + teamId + " is not exist. "));
        return playerMapper.toPlayerDtoList(team.getPlayers());
    }

    @Override
    public TeamDTO addTeam(TeamDTO teamDTO) {
        Team team = teamRepository.save(teamMapper.toTeam(teamDTO));

        TeamStats teamStats = new TeamStats();
        teamStats.setTeam(team);

        TeamStandings teamStandings = new TeamStandings();
        teamStandings.setTeam(team);

        teamStatsRepository.save(teamStats);
        teamStandingsRepository.save(teamStandings);

        return teamMapper.toTeamDto(team);
    }

    @Override
    public void deleteTeam(Integer teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team with id: " + teamId + " is not exist"));
        teamRepository.delete(team);
    }

    @Override
    public TeamDTO updateTeam(Integer teamId, TeamDTO teamDTO) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team with id: " + teamId + " is not exist."));
        teamMapper.updateTeamFromTeamDTO(teamDTO, team);
        teamRepository.save(team);
        return teamMapper.toTeamDto(team);
    }

    @Override
    public List<MatchDTO> getMatchesByTeam(Integer teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team with ID: " + teamId + " is not exist."));
        return matchMapper.toMatchDTOList(team.getAllMatches());
    }
}
