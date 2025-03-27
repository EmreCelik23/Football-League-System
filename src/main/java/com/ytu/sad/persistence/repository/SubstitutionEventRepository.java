package com.ytu.sad.persistence.repository;

import com.ytu.sad.persistence.entity.SubstitutionEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubstitutionEventRepository extends JpaRepository<SubstitutionEvent, Integer> {

}
