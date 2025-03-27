package com.ytu.sad.persistence.repository;

import com.ytu.sad.persistence.entity.TeamStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamStatsRepository extends JpaRepository<TeamStats, Integer> {
    TeamStats findByTeamId(Integer teamId);
}
