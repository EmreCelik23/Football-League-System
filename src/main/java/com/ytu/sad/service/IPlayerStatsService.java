package com.ytu.sad.service;

import com.ytu.sad.persistence.dto.PlayerStatsDTO;

import java.util.List;

public interface IPlayerStatsService {
    List<PlayerStatsDTO> getAllPlayerStats();

    PlayerStatsDTO getPlayerStatsByPlayerId(Integer playerId);

    void deletePlayerStatsByPlayerId(Integer playerId);

    void resetAllPlayerStats();
}
