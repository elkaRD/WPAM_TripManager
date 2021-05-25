package com.elkard.tripmanager.services;

import com.elkard.tripmanager.model.dao.PlaceRepository;
import com.elkard.tripmanager.model.dao.PlaceVoteRepository;
import com.elkard.tripmanager.model.dao.UsersTripRepository;
import com.elkard.tripmanager.model.entities.Place;
import com.elkard.tripmanager.model.entities.PlaceVote;
import com.elkard.tripmanager.model.entities.Trip;
import com.elkard.tripmanager.dto.PlaceDetails;
import com.elkard.tripmanager.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlaceDetailsService {

    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private PlaceVoteRepository placeVoteRepository;

    public List<PlaceDetails> getPlaces(Trip trip, User user)
    {
        List<Place> places = placeRepository.findByTripTripId(trip.getTripId());
        List<PlaceVote> selected;
        if (!trip.getAccepted())
            selected = placeVoteRepository.findByUserIdAndTripTripId(user.getUserId(), trip.getTripId());
        else
            selected = placeVoteRepository.findByUserIdAndTripTripId(trip.getHost().getUserId(), trip.getTripId());

        Set<Long> selectedIds = new HashSet<>();
        for (PlaceVote vote : selected)
            selectedIds.add(vote.getPlaceId());

        List<PlaceDetails> result = new ArrayList<>();

        for (Place place : places)
        {
            PlaceDetails placeDetails = new PlaceDetails(place);
            placeDetails.setSelected(selectedIds.contains(placeDetails.getPlaceId()));
            placeDetails.setVotes(placeVoteRepository.countByPlaceId(place.getPlaceId()));
            result.add(placeDetails);
        }

        return result;
    }
}
