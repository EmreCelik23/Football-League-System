package com.ytu.sad.persistence.repository;

import com.ytu.sad.persistence.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {

}
