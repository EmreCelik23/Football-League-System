package com.ytu.sad.controller;

import com.ytu.sad.persistence.dto.TeamStatsDTO;
import com.ytu.sad.service.ITeamStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/team-stats")
@CrossOrigin(origins = "*")
public class TeamStatsController {

    private final ITeamStatsService teamStatsService;

    @Autowired
    public TeamStatsController(ITeamStatsService teamStatsService) {
        this.teamStatsService = teamStatsService;
    }

    @GetMapping
    public ResponseEntity<List<TeamStatsDTO>> getAllTeamStats() {
        return ResponseEntity.ok(teamStatsService.getAllTeamStats());
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<TeamStatsDTO> getTeamStatsByTeamId(@PathVariable Integer teamId) {
        return ResponseEntity.ok(teamStatsService.getTeamStatsByTeamId(teamId));
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<Object> deleteTeamStatsByTeamId(@PathVariable Integer teamId) {
        teamStatsService.deleteTeamStatsByTeamId(teamId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/reset")
    public ResponseEntity<Object> resetAllTeamStats() {
        teamStatsService.resetAllTeamStats();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
