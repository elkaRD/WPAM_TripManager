package com.elkard.tripmanager.services;

import com.elkard.tripmanager.model.dao.TripDateRepository;
import com.elkard.tripmanager.model.entities.Trip;
import com.elkard.tripmanager.model.entities.TripDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TripDateService {

    @Autowired
    private TripDateRepository tripDateRepository;

    public List<String> getTripDates(Trip trip)
    {
        List<TripDate> days = tripDateRepository.findByTripIdOrderByDay(trip.getTripId());
        List<String> result = new ArrayList<>();
        for (TripDate day : days)
        {
            result.add(day.getDay());
        }
        return result;
    }
}
