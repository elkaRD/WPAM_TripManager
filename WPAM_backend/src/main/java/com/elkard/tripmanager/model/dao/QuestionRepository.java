package com.elkard.tripmanager.model.dao;

import com.elkard.tripmanager.model.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByTripTripId(Long tripId);
    long deleteByTripTripId(Long tripId);
}
