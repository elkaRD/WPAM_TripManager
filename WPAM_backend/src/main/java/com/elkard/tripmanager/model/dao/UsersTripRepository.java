package com.elkard.tripmanager.model.dao;

import com.elkard.tripmanager.model.compositekeys.UsersTripCompositeKey;
import com.elkard.tripmanager.model.entities.UsersTrip;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface UsersTripRepository extends JpaRepository<UsersTrip, UsersTripCompositeKey> {

    List<UsersTrip> findByUserId(Long userId);
    List<UsersTrip> findByTripId(Long tripId);
    UsersTrip findByUserIdAndTripId(Long userId, Long tripId);

    long deleteByTripId(Long tripId);
    long deleteByUserIdAndTripId(Long userId, Long tripId);
}
