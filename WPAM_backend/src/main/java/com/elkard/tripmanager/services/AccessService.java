package com.elkard.tripmanager.services;

import com.elkard.tripmanager.exceptions.TripNotFoundException;
import com.elkard.tripmanager.model.dao.UsersTripRepository;
import com.elkard.tripmanager.model.entities.Trip;
import com.elkard.tripmanager.model.entities.User;
import com.elkard.tripmanager.model.entities.UsersTrip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessService {

    @Autowired
    private UsersTripRepository usersTripRepository;

    public void checkAccessToTrip(User user, Trip trip) throws TripNotFoundException
    {
        UsersTrip usersTrip = usersTripRepository.findByUserIdAndTripId(user.getUserId(), trip.getTripId());
        if (usersTrip == null)
        {
            throw new TripNotFoundException();
        }
    }
}
