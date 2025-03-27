package com.ytu.sad.persistence.repository;

import com.ytu.sad.persistence.entity.GoalEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalEventRepository extends JpaRepository<GoalEvent, Integer> {

}
