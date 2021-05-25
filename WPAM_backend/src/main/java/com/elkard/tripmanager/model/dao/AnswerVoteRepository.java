package com.elkard.tripmanager.model.dao;

import com.elkard.tripmanager.model.entities.AnswerVote;
import com.elkard.tripmanager.model.compositekeys.AnswerVoteCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface AnswerVoteRepository extends JpaRepository<AnswerVote, AnswerVoteCompositeKey> {

    List<AnswerVote> findByUserId(Long userId);
    List<AnswerVote> findByQuestionQuestionId(Long questionId);
    AnswerVote findByUserIdAndAnswerId(Long userId, Long answerId);

    long countByAnswerId(Long answerId);

    long deleteByTripTripId(Long tripId);
    long deleteByUserIdAndTripTripId(Long userId, Long tripId);
    long deleteByUserIdAndAnswerId(Long userId, Long answerId);
}
