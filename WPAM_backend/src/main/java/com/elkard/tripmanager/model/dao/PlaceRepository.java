package com.elkard.tripmanager.model.dao;

import com.elkard.tripmanager.model.entities.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface PlaceRepository extends JpaRepository<Place, Long> {

    List<Place> findByTripTripId(Long tripId);
    List<Place> findByTripTripIdAndUserUserId(Long tripId, Long userId);

    long deleteByTripTripId(Long tripId);
}
