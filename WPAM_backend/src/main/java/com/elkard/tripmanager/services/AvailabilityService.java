package com.elkard.tripmanager.services;

import com.elkard.tripmanager.dto.DateDetails;
import com.elkard.tripmanager.dto.requests.availability.SubmitAvailabilityRequest;
import com.elkard.tripmanager.dto.responses.BaseResponse;
import com.elkard.tripmanager.exceptions.RequestParamsException;
import com.elkard.tripmanager.model.dao.AvailabilityRepository;
import com.elkard.tripmanager.model.dao.TripDateRepository;
import com.elkard.tripmanager.model.dao.TripRepository;
import com.elkard.tripmanager.model.entities.Availability;
import com.elkard.tripmanager.model.entities.Trip;
import com.elkard.tripmanager.model.entities.TripDate;
import com.elkard.tripmanager.model.entities.User;
import com.elkard.tripmanager.utils.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AvailabilityService {

    private static final Logger log = LogManager.getLogger(AvailabilityService.class);

    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private AccessService accessService;
    @Autowired
    private TripDateService tripDateService;

    public List<String> getTripAvailability(Trip trip, User user)
    {
        List<Availability> days = availabilityRepository.findByTripIdAndUserIdOrderByDay(trip.getTripId(), user.getUserId());
        List<String> result = new ArrayList<>();
        for (Availability day : days)
        {
            result.add(day.getDay());
        }
        return result;
    }

    public List<DateDetails> getDateVotes(Trip trip)
    {
        List<Availability> days = availabilityRepository.findByTripId(trip.getTripId());
        Map<String, Long> votes = new TreeMap<>();
        for (Availability day : days)
        {
            if (votes.containsKey(day.getDay()))
                votes.put(day.getDay(), votes.get(day.getDay()) + 1);
            else
                votes.put(day.getDay(), 1L);
        }
        List<DateDetails> result = new ArrayList<>();
        for (String day : votes.keySet())
        {
            DateDetails dateDetails = new DateDetails();
            dateDetails.setDay(DateUtils.formatDate(day));
            dateDetails.setVotes(votes.get(day));
            result.add(dateDetails);
        }
        return result;
    }

    public BaseResponse submitAvailability(SubmitAvailabilityRequest request)
    {
        log.info("START submitAvailability");

        BaseResponse response = new BaseResponse();

        try
        {
            User user = userService.getUserFromRequest(request);
            Trip trip = tripRepository.getOne(request.getTripId());

            //TODO check if provided deviceId should have access to this trip
            accessService.checkAccessToTrip(user, trip);

            submitAvailability(user, trip, request.getSelectedDates());

            log.info("SUCCESS submitAvailability");
        }
        catch (Exception e)
        {
            response.setErrorCode("-1");
            response.setErrorDesc(e.getMessage());
            log.error("ERROR submitAvailability: " + e.getMessage(), e);
        }

        return response;
    }

    @Transactional(rollbackFor = Exception.class)
    private void submitAvailability(User user, Trip trip, List<String> selectedPlaceIds) throws RequestParamsException
    {
        Set<String> possibleDates = new HashSet<>();
        List<String> tripDates = tripDateService.getTripDates(trip);
        possibleDates.addAll(tripDates);
        for (String selectedDate : selectedPlaceIds)
        {
            if (!possibleDates.contains(selectedDate))
            {
                throw new RequestParamsException("Date " + selectedDate + " is not possible for this trip");
            }
        }

        Set<String> selectedBefore = new HashSet<>();
        Set<String> selectedAfter = new HashSet<>();

        List<Availability> availableDates = availabilityRepository.findByTripIdAndUserIdOrderByDay(trip.getTripId(), user.getUserId());

        for (Availability date : availableDates)
            selectedBefore.add(date.getDay());

        for (String date : selectedPlaceIds)
            selectedAfter.add(date);

        for (String date : selectedBefore)
        {
            if (!selectedAfter.contains(date))
            {
                availabilityRepository.deleteByUserIdAndTripIdAndDay(user.getUserId(), trip.getTripId(), date);
            }
        }

        for (String date: selectedAfter)
        {
            if (!selectedBefore.contains(date))
            {
                Availability available = new Availability();
                available.setUserId(user.getUserId());
                available.setTripId(trip.getTripId());
                available.setDay(date);
                availabilityRepository.save(available);
            }
        }
    }
}
