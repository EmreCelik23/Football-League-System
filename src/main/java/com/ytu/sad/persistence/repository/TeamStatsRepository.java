package com.ytu.sad.persistence.repository;

import com.ytu.sad.persistence.entity.TeamStats;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamStatsRepository extends JpaRepository<TeamStats, Integer> {
    TeamStats findByTeamId(Integer teamId);

    @Modifying
    @Transactional
    @Query("UPDATE TeamStats ts SET ts.matchesPlayed = 0, ts.points = 0, ts.goalsScored = 0, ts.goalsConceded = 0, ts.assists = 0, ts.yellowCards = 0, ts.redCards = 0, ts.cleanSheets  = 0")
    void resetAll();
}
