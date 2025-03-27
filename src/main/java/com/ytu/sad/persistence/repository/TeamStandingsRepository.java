package com.ytu.sad.persistence.repository;

import com.ytu.sad.persistence.entity.TeamStandings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamStandingsRepository extends JpaRepository<TeamStandings, Integer> {
    TeamStandings findByTeamId(Integer teamId);
}
