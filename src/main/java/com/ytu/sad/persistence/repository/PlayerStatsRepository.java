package com.ytu.sad.persistence.repository;

import com.ytu.sad.persistence.entity.PlayerStats;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerStatsRepository extends JpaRepository<PlayerStats, Integer> {
    PlayerStats findByPlayerId(Integer playerId);

    @Modifying
    @Transactional
    @Query("UPDATE PlayerStats ps SET ps.matchesPlayed = 0, ps.goals = 0, ps.assists = 0, ps.yellowCards = 0, ps.redCards = 0, ps.minutesPlayed = 0")
    void resetAll();
}
