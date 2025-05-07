package com.ytu.sad.controller;

import com.ytu.sad.persistence.dto.TeamStandingsDTO;
import com.ytu.sad.service.ITeamStandingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/standings")
@CrossOrigin(origins = "*")
public class TeamStandingsController {

    private final ITeamStandingsService teamStandingsService;

    @Autowired
    public TeamStandingsController(ITeamStandingsService teamStandingsService) {
        this.teamStandingsService = teamStandingsService;
    }

    @GetMapping
    public ResponseEntity<List<TeamStandingsDTO>> getAllTeamStandings() {
        return ResponseEntity.ok(teamStandingsService.getAllTeamStandings());
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<TeamStandingsDTO> getTeamStandingsByTeamId(@PathVariable Integer teamId) {
        return ResponseEntity.ok(teamStandingsService.getTeamStandingsByTeamId(teamId));
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<Object> deleteTeamStandingsByTeamId(@PathVariable Integer teamId) {
        teamStandingsService.deleteTeamStandingsByTeamId(teamId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/reset")
    public ResponseEntity<Object> resetAllTeamStandings() {
        teamStandingsService.resetAllTeamStandings();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
