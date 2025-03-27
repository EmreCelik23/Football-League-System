package com.ytu.sad.service.impl;

import com.ytu.sad.exception.ResourceNotFoundException;
import com.ytu.sad.persistence.dto.PlayerStatsDTO;
import com.ytu.sad.persistence.entity.PlayerStats;
import com.ytu.sad.persistence.mapper.PlayerStatsMapper;
import com.ytu.sad.persistence.repository.PlayerStatsRepository;
import com.ytu.sad.service.IPlayerStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerStatsServiceImpl implements IPlayerStatsService {

    private final PlayerStatsMapper playerStatsMapper;
    private final PlayerStatsRepository playerStatsRepository;

    @Autowired
    public PlayerStatsServiceImpl(PlayerStatsMapper playerStatsMapper, PlayerStatsRepository playerStatsRepository) {
        this.playerStatsMapper = playerStatsMapper;
        this.playerStatsRepository = playerStatsRepository;
    }

    @Override
    public List<PlayerStatsDTO> getAllPlayerStats() {
        List<PlayerStats> playerStatsList = playerStatsRepository.findAll();
        return playerStatsMapper.toPlayerStatsDTOList(playerStatsList);
    }

    @Override
    public PlayerStatsDTO getPlayerStatsByPlayerId(Integer playerId) {
        PlayerStats playerStats = playerStatsRepository.findByPlayerId(playerId);
        if (playerStats == null) {
            throw new ResourceNotFoundException("Stats of Player with ID: " + playerId + " not found");
        }
        return playerStatsMapper.toPlayerStatsDTO(playerStats);
    }

    @Override
    public void deletePlayerStatsByPlayerId(Integer playerId) {
        PlayerStats playerStats = playerStatsRepository.findByPlayerId(playerId);
        if (playerStats == null) {
            throw new ResourceNotFoundException("Stats of Player with ID: " + playerId + " not found");
        }
        playerStatsRepository.delete(playerStats);
    }
}
