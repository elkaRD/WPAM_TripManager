package com.elkard.tripmanager.model.dao;

import com.elkard.tripmanager.model.entities.PlaceVote;
import com.elkard.tripmanager.model.compositekeys.PlaceVoteCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface PlaceVoteRepository extends JpaRepository<PlaceVote, PlaceVoteCompositeKey> {

    List<PlaceVote> findByUserIdAndTripTripId(Long userId, Long tripId);
    List<PlaceVote> findByTripTripId(Long tripId);
    PlaceVote findByUserIdAndPlaceId(Long userId, Long placeId);

    long countByPlaceId(Long placeId);

    long deleteByTripTripId(Long tripId);
    long deleteByUserIdAndTripTripId(Long userId, Long tripId);
    long deleteByUserIdAndPlaceId(Long userId, Long placeId);
}
