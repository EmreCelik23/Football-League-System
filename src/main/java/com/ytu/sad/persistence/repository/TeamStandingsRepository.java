package com.ytu.sad.persistence.repository;

import com.ytu.sad.persistence.entity.TeamStandings;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamStandingsRepository extends JpaRepository<TeamStandings, Integer> {
    TeamStandings findByTeamId(Integer teamId);

    @Modifying
    @Transactional
    @Query("UPDATE TeamStandings ts SET ts.matchesPlayed = 0, ts.points = 0, ts.goalsScored = 0, ts.goalsConceded = 0, ts.wins = 0, ts.draws = 0, ts.losses = 0, ts.goalDifference  = 0")
    void resetAll();
}
