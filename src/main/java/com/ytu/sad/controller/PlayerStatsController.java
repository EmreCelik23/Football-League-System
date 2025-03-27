package com.ytu.sad.controller;

import com.ytu.sad.persistence.dto.PlayerStatsDTO;
import com.ytu.sad.service.IPlayerStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/player-stats")
@CrossOrigin(origins = "*")
public class PlayerStatsController {

    private final IPlayerStatsService playerStatsService;

    @Autowired
    public PlayerStatsController(IPlayerStatsService playerStatsService) {
        this.playerStatsService = playerStatsService;
    }

    @GetMapping
    public ResponseEntity<List<PlayerStatsDTO>> getAllPlayerStats() {
        return ResponseEntity.ok(playerStatsService.getAllPlayerStats());
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<PlayerStatsDTO> getPlayerStatsByPlayerId(@PathVariable Integer playerId) {
        return ResponseEntity.ok(playerStatsService.getPlayerStatsByPlayerId(playerId));
    }

    @DeleteMapping("/{playerId}")
    public ResponseEntity<Object> deletePlayerStatsByPlayerId(@PathVariable Integer playerId) {
        playerStatsService.deletePlayerStatsByPlayerId(playerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
