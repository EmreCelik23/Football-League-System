package com.ytu.sad.service.impl;

import com.ytu.sad.exception.ResourceNotFoundException;
import com.ytu.sad.persistence.dto.TeamStatsDTO;
import com.ytu.sad.persistence.entity.TeamStats;
import com.ytu.sad.persistence.mapper.TeamStatsMapper;
import com.ytu.sad.persistence.repository.TeamStatsRepository;
import com.ytu.sad.service.ITeamStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamStatsServiceImpl implements ITeamStatsService {

    private final TeamStatsMapper teamStatsMapper;
    private final TeamStatsRepository teamStatsRepository;

    @Autowired
    public TeamStatsServiceImpl(TeamStatsMapper teamStatsMapper, TeamStatsRepository teamStatsRepository) {
        this.teamStatsMapper = teamStatsMapper;
        this.teamStatsRepository = teamStatsRepository;
    }

    @Override
    public List<TeamStatsDTO> getAllTeamStats() {
        List<TeamStats> teamStatsList = teamStatsRepository.findAll();
        return teamStatsMapper.toTeamStatsDTOList(teamStatsList);
    }

    @Override
    public TeamStatsDTO getTeamStatsByTeamId(Integer teamId) {
        TeamStats teamStats = teamStatsRepository.findByTeamId(teamId);
        if (teamStats == null) {
            throw new ResourceNotFoundException("Stats of Team with ID: " + teamId + " not found");
        }
        return teamStatsMapper.toTeamStatsDTO(teamStats);
    }

    @Override
    public void deleteTeamStatsByTeamId(Integer teamId) {
        TeamStats teamStats = teamStatsRepository.findByTeamId(teamId);
        if (teamStats == null) {
            throw new ResourceNotFoundException("Stats of Team with ID: " + teamId + " not found");
        }
        teamStatsRepository.delete(teamStats);
    }
}
