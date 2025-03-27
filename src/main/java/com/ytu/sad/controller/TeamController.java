package com.ytu.sad.controller;

import com.ytu.sad.persistence.dto.MatchDTO;
import com.ytu.sad.persistence.dto.PlayerDTO;
import com.ytu.sad.persistence.dto.TeamDTO;
import com.ytu.sad.service.ITeamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/team")
@CrossOrigin(origins = "*")
public class TeamController {

    private final ITeamService teamService;

    @Autowired
    public TeamController(ITeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public ResponseEntity<List<TeamDTO>> getAllTeams(){
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable Integer teamId){
        return ResponseEntity.ok(teamService.getTeamById(teamId));
    }

    @GetMapping("/{teamId}/players")
    public ResponseEntity<List<PlayerDTO>> getPlayersByTeam(@PathVariable Integer teamId){
        return ResponseEntity.ok(teamService.getPlayersByTeam(teamId));
    }

    @PostMapping
    public ResponseEntity<TeamDTO> addTeam(@Valid @RequestBody TeamDTO teamDTO){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(teamService.addTeam(teamDTO));
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<Object> deleteTeam(@PathVariable Integer teamId){
        teamService.deleteTeam(teamId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{teamId}")
    public ResponseEntity<TeamDTO> updateTeam(@PathVariable Integer teamId, @Valid @RequestBody TeamDTO teamDTO){
        return ResponseEntity.ok(teamService.updateTeam(teamId, teamDTO));
    }

    @GetMapping("/{teamId}/matches")
    public ResponseEntity<List<MatchDTO>> getMatchesByTeam(@PathVariable Integer teamId){
        return ResponseEntity.ok(teamService.getMatchesByTeam(teamId));
    }
}
