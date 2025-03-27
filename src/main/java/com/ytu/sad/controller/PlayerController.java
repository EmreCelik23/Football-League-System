package com.ytu.sad.controller;

import com.ytu.sad.persistence.dto.PlayerDTO;
import com.ytu.sad.service.IPlayerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/player")
@CrossOrigin(origins = "*")
public class PlayerController {

    private final IPlayerService playerService;

    @Autowired
    public PlayerController(IPlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public ResponseEntity<List<PlayerDTO>> getAllPlayers(){
        return ResponseEntity.ok(playerService.getAllPlayers());
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<PlayerDTO> getPlayerById(@PathVariable Integer playerId){
        return ResponseEntity.ok(playerService.getPlayerById(playerId));
    }

    @PostMapping
    public ResponseEntity<PlayerDTO> addPlayer(@Valid @RequestBody PlayerDTO playerDTO){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(playerService.addPlayer(playerDTO));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<PlayerDTO>> addMultiplePlayers(@Valid @RequestBody List<PlayerDTO> playerDTOs){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(playerService.addMultiplePlayers(playerDTOs));
    }

    @DeleteMapping("/{playerId}")
    public ResponseEntity<Object> deletePlayer(@PathVariable Integer playerId){
        playerService.deletePlayer(playerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{playerId}")
    public ResponseEntity<PlayerDTO> updatePlayer(@PathVariable Integer playerId, @Valid @RequestBody PlayerDTO playerDTO){
        return ResponseEntity.ok(playerService.updatePlayer(playerId, playerDTO));
    }
}
