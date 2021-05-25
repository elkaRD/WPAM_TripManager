package com.elkard.tripmanager.model.dao;

import com.elkard.tripmanager.model.entities.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findByQuestionQuestionId(Long questionId);
    long deleteByTripTripId(Long tripId);
}
