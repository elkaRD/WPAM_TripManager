package com.elkard.tripmanager.model.dao;

import com.elkard.tripmanager.model.entities.Availability;
import com.elkard.tripmanager.model.compositekeys.AvailabilityCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface AvailabilityRepository extends JpaRepository<Availability, AvailabilityCompositeKey> {

    List<Availability> findByTripIdAndUserIdOrderByDay(Long tripId, Long userId);
    List<Availability> findByTripId(Long tripId);

    long deleteByTripId(Long tripId);
    long deleteByUserIdAndTripId(Long userId, Long tripId);
    long deleteByUserIdAndTripIdAndDay(Long userId, Long tripId, String day);
}
