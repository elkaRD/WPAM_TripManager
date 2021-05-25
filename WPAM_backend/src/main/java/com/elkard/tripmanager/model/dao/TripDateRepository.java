package com.elkard.tripmanager.model.dao;

import com.elkard.tripmanager.model.compositekeys.TripDateCompositeKey;
import com.elkard.tripmanager.model.entities.TripDate;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface TripDateRepository extends JpaRepository<TripDate, TripDateCompositeKey> {

    List<TripDate> findByTripIdOrderByDay(Long tripId);
    long deleteByTripId(Long tripId);
}
