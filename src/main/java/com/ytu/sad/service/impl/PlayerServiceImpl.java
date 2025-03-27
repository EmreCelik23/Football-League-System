package com.ytu.sad.service.impl;

import com.ytu.sad.exception.ResourceNotFoundException;
import com.ytu.sad.persistence.dto.PlayerDTO;
import com.ytu.sad.persistence.entity.Player;
import com.ytu.sad.persistence.entity.PlayerStats;
import com.ytu.sad.persistence.entity.Team;
import com.ytu.sad.persistence.mapper.PlayerMapper;
import com.ytu.sad.persistence.repository.PlayerRepository;
import com.ytu.sad.persistence.repository.PlayerStatsRepository;
import com.ytu.sad.persistence.repository.TeamRepository;
import com.ytu.sad.service.IPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements IPlayerService {

    private final PlayerMapper playerMapper;

    private final PlayerRepository playerRepository;

    private final TeamRepository teamRepository;
    private final PlayerStatsRepository playerStatsRepository;

    @Autowired
    public PlayerServiceImpl(PlayerMapper playerMapper, PlayerRepository playerRepository, TeamRepository teamRepository, PlayerStatsRepository playerStatsRepository) {
        this.playerMapper = playerMapper;
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.playerStatsRepository = playerStatsRepository;
    }

    @Override
    public void deletePlayer(Integer playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new ResourceNotFoundException("Player with ID: " + playerId + " is not exist."));
        playerRepository.delete(player);
    }

    @Override
    public PlayerDTO updatePlayer(Integer playerId, PlayerDTO playerDTO) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new ResourceNotFoundException("Player with ID: " + playerId + " is not exist."));
        Team team = teamRepository.findById(playerDTO.getTeamId())
                .orElseThrow(() -> new ResourceNotFoundException("Team with ID: " + playerDTO.getTeamId() + " is not exist."));
        playerMapper.updatePlayerFromPlayerDTO(playerDTO, player);
        player.setTeam(team);
        playerRepository.save(player);
        return playerMapper.toPlayerDto(player);
    }

    @Override
    public PlayerDTO addPlayer(PlayerDTO playerDTO) {
        Team team = teamRepository.findById(playerDTO.getTeamId())
                .orElseThrow(() -> new ResourceNotFoundException("Team with ID: " + playerDTO.getTeamId() + " is not exist."));
        Player player = playerMapper.toPlayer(playerDTO);
        player.setTeam(team);

        PlayerStats playerStats = new PlayerStats();
        playerStats.setPlayer(player);

        playerRepository.save(player);
        playerStatsRepository.save(playerStats);
        return playerMapper.toPlayerDto(player);
    }

    @Override
    public PlayerDTO getPlayerById(Integer playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new ResourceNotFoundException("Player with ID: " + playerId + " is not exist."));
        return playerMapper.toPlayerDto(player);
    }

    @Override
    public List<PlayerDTO> getAllPlayers() {
        List<Player> players = playerRepository.findAll();
        return playerMapper.toPlayerDtoList(players);
    }

    @Override
    public List<PlayerDTO> addMultiplePlayers(List<PlayerDTO> playerDTOs) {
        return playerDTOs.stream()
                .map(this::addPlayer)
                .toList();
    }
}
