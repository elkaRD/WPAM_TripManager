package com.elkard.tripmanager.model.dao;

import com.elkard.tripmanager.model.entities.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface TripRepository extends JpaRepository<Trip, Long> {

    Trip findByCode(String code);
    long deleteByTripId(Long tripId);
}
