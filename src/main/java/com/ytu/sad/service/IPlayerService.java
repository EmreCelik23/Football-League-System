package com.ytu.sad.service;

import com.ytu.sad.persistence.dto.PlayerDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface IPlayerService {
    void deletePlayer(Integer playerId);

    PlayerDTO updatePlayer(Integer playerId, PlayerDTO playerDTO);

    PlayerDTO addPlayer(PlayerDTO playerDTO);

    PlayerDTO getPlayerById(Integer playerId);

    List<PlayerDTO> getAllPlayers();

    List<PlayerDTO> addMultiplePlayers(@Valid List<PlayerDTO> playerDTOs);
}
