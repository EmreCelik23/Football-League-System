package com.ytu.sad.service.impl;

import com.ytu.sad.exception.ResourceNotFoundException;
import com.ytu.sad.persistence.dto.TeamStandingsDTO;
import com.ytu.sad.persistence.entity.TeamStandings;
import com.ytu.sad.persistence.mapper.TeamStandingsMapper;
import com.ytu.sad.persistence.repository.TeamStandingsRepository;
import com.ytu.sad.service.ITeamStandingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamStandingsServiceImpl implements ITeamStandingsService {

    private final TeamStandingsMapper teamStandingsMapper;
    private final TeamStandingsRepository teamStandingsRepository;

    @Autowired
    public TeamStandingsServiceImpl(TeamStandingsMapper teamStandingsMapper, TeamStandingsRepository teamStandingsRepository) {
        this.teamStandingsMapper = teamStandingsMapper;
        this.teamStandingsRepository = teamStandingsRepository;
    }

    @Override
    public List<TeamStandingsDTO> getAllTeamStandings() {
        List<TeamStandings> teamStandingsList = teamStandingsRepository.findAll();
        return teamStandingsMapper.toTeamStandingsDTOList(teamStandingsList);
    }

    @Override
    public TeamStandingsDTO getTeamStandingsByTeamId(Integer teamId) {
        TeamStandings teamStandings = teamStandingsRepository.findByTeamId(teamId);
        if (teamStandings == null) {
            throw new ResourceNotFoundException("Standings of Team with ID: " + teamId + " not found");
        }
        return teamStandingsMapper.toTeamStandingsDTO(teamStandings);
    }

    @Override
    public void deleteTeamStandingsByTeamId(Integer teamId) {
        TeamStandings teamStandings = teamStandingsRepository.findByTeamId(teamId);
        if (teamStandings == null) {
            throw new ResourceNotFoundException("Standings of Team with ID: " + teamId + " not found");
        }
        teamStandingsRepository.delete(teamStandings);
    }
}
