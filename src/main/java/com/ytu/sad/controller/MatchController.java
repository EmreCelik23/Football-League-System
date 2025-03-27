package com.ytu.sad.controller;

import com.ytu.sad.persistence.dto.*;
import com.ytu.sad.service.IMatchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/match")
@CrossOrigin(origins = "*")
public class MatchController {

    private final IMatchService matchService;

    @Autowired
    public MatchController(IMatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping
    public ResponseEntity<List<MatchDTO>> getAllMatches(){
        return ResponseEntity.ok(matchService.getAllMatches());
    }

    @GetMapping("/{matchId}")
    public ResponseEntity<MatchDTO> getMatchById(@PathVariable Integer matchId){
        return ResponseEntity.ok(matchService.getMatchById(matchId));
    }

    @PostMapping
    public ResponseEntity<MatchDTO> createMatch(@Valid @RequestBody MatchDTO matchDTO){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(matchService.createMatch(matchDTO));
    }

    @PutMapping("/{matchId}")
    public ResponseEntity<MatchDTO> updateMatch(@PathVariable Integer matchId, @Valid @RequestBody MatchDTO matchDTO){
        return ResponseEntity.ok(matchService.updateMatch(matchId, matchDTO));
    }

    @DeleteMapping("/{matchId}")
    public ResponseEntity<Object> deleteMatch(@PathVariable Integer matchId){
        matchService.deleteMatch(matchId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{matchId}/goal")
    public ResponseEntity<MatchDTO> addGoal(@PathVariable Integer matchId, @Valid @RequestBody GoalEventDTO goalEventDTO){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(matchService.addGoal(matchId, goalEventDTO));
    }

    @PostMapping("/{matchId}/card")
    public ResponseEntity<MatchDTO> addCard(@PathVariable Integer matchId, @Valid @RequestBody CardEventDTO cardEventDTO){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(matchService.addCard(matchId, cardEventDTO));
    }

    @DeleteMapping("/{matchId}/goal/{goalId}")
    public ResponseEntity<Object> deleteGoal(@PathVariable Integer matchId, @PathVariable Integer goalId){
        matchService.deleteGoal(matchId, goalId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{matchId}/card/{cardId}")
    public ResponseEntity<Object> deleteCard(@PathVariable Integer matchId, @PathVariable Integer cardId){
        matchService.deleteCard(matchId, cardId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{matchId}/lineups")
    public ResponseEntity<LineupsDTO> getMatchLineups(@PathVariable Integer matchId){
        return ResponseEntity.ok(matchService.getMatchLineups(matchId));
    }

    @PostMapping("/{matchId}/lineups")
    public ResponseEntity<LineupsDTO> setMatchLineups(@PathVariable Integer matchId, @Valid @RequestBody LineupsDTO lineups){
        return ResponseEntity.ok(matchService.setMatchLineups(matchId, lineups));
    }

    @PostMapping("/{matchId}/substitution")
    public ResponseEntity<MatchDTO> addSubstitution(@PathVariable Integer matchId, @Valid @RequestBody SubstitutionEventDTO substitution){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(matchService.addSubstitution(matchId, substitution));
    }

    @DeleteMapping("/{matchId}/substitution/{substitutionId}")
    public ResponseEntity<Object> deleteSubstitution(@PathVariable Integer matchId, @PathVariable Integer substitutionId){
        matchService.deleteSubstitution(matchId, substitutionId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{matchId}/start")
    public ResponseEntity<MatchDTO> startMatch(@PathVariable Integer matchId){
        return ResponseEntity.ok(matchService.startMatch(matchId));
    }

    @PostMapping("/{matchId}/finish")
    public ResponseEntity<MatchDTO> finishMatch(@PathVariable Integer matchId){
        return ResponseEntity.ok(matchService.finishMatch(matchId));
    }
}
