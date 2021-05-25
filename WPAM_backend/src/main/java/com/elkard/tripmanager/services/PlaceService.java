package com.elkard.tripmanager.services;

import com.elkard.tripmanager.dto.requests.place.AddPlaceRequest;
import com.elkard.tripmanager.dto.requests.place.SelectPlaceRequest;
import com.elkard.tripmanager.dto.requests.place.SubmitPlaceRequest;
import com.elkard.tripmanager.dto.responses.BaseResponse;
import com.elkard.tripmanager.exceptions.RequestParamsException;
import com.elkard.tripmanager.model.dao.PlaceRepository;
import com.elkard.tripmanager.model.dao.PlaceVoteRepository;
import com.elkard.tripmanager.model.dao.TripRepository;
import com.elkard.tripmanager.model.entities.Place;
import com.elkard.tripmanager.model.entities.PlaceVote;
import com.elkard.tripmanager.model.entities.Trip;
import com.elkard.tripmanager.model.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PlaceService
{
    private static final Logger log = LogManager.getLogger(PlaceService.class);

    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private PlaceVoteRepository placeVoteRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private AccessService accessService;

    public BaseResponse addPlace(AddPlaceRequest request)
    {
        log.info("START addPlace");

        BaseResponse response = new BaseResponse();

        try
        {
            User user = userService.getUserFromRequest(request);
            Trip trip = tripRepository.getOne(request.getTripId());

            //TODO check if provided deviceId should have access to this trip
            accessService.checkAccessToTrip(user, trip);

            if (request.getContainsCoords()) {
                if (request.getCoordLat() == null || request.getCoordLon() == null)
                    throw new RequestParamsException("ContainsCoord is true but there is no coords");
            }
            addPlace(user, trip, request);

            log.info("SUCCESS addPlace");
        }
        catch (Exception e)
        {
            response.setErrorCode("-1");
            response.setErrorDesc(e.getMessage());
            log.error("ERROR addPlace: " + e.getMessage(), e);
        }

        return response;
    }

    public BaseResponse submitPlace(SubmitPlaceRequest request)
    {
        log.info("START submitPlace");

        BaseResponse response = new BaseResponse();

        try
        {
            User user = userService.getUserFromRequest(request);
            Trip trip = tripRepository.getOne(request.getTripId());

            //TODO check if provided deviceId should have access to this trip
            accessService.checkAccessToTrip(user, trip);

            submitPlace(user, trip, request.getSelectedPlaces());

            log.info("SUCCESS submitPlace");
        }
        catch (Exception e)
        {
            response.setErrorCode("-1");
            response.setErrorDesc(e.getMessage());
            log.error("ERROR submitPlace: " + e.getMessage(), e);
        }

        return response;
    }

    public BaseResponse selectPlace(SelectPlaceRequest request)
    {
        log.info("START selectPlace");

        BaseResponse response = new BaseResponse();

        try
        {
            User user = userService.getUserFromRequest(request);
            Trip trip = tripRepository.getOne(request.getTripId());

            //TODO check if provided deviceId should have access to this trip
            accessService.checkAccessToTrip(user, trip);

            selectPlace(user, trip, request.getPlaceId(), request.getSelected());

            log.info("SUCCESS selectPlace");
        }
        catch (Exception e)
        {
            response.setErrorCode("-1");
            response.setErrorDesc(e.getMessage());
            log.error("ERROR selectPlace: " + e.getMessage(), e);
        }

        return response;
    }

    @Transactional(rollbackFor = Exception.class)
    private void addPlace(User user, Trip trip, AddPlaceRequest request)
    {
        Place place = new Place();
        place.setTrip(trip);
        place.setUser(user);
        place.setName(request.getName());
        place.setAccepted(false);
        if (request.getContainsCoords())
        {
            place.setLatitude(request.getCoordLat());
            place.setLongitude(request.getCoordLon());
        }
        placeRepository.save(place);
    }

    @Transactional(rollbackFor = Exception.class)
    private void submitPlace(User user, Trip trip, List<Long> selectedPlaceIds)
    {
        Set<Long> selectedBefore = new HashSet<>();
        Set<Long> selectedAfter = new HashSet<>();

        List<PlaceVote> placeVotes = placeVoteRepository.findByUserIdAndTripTripId(user.getUserId(), trip.getTripId());

        for (PlaceVote vote : placeVotes)
            selectedBefore.add(vote.getPlaceId());

        for (Long placeId : selectedPlaceIds)
            selectedAfter.add(placeId);

        for (Long placeId : selectedBefore)
        {
            if (!selectedAfter.contains(placeId))
            {
                placeVoteRepository.deleteByUserIdAndPlaceId(user.getUserId(), placeId);
            }
        }

        for (Long placeId : selectedAfter)
        {
            if (!selectedBefore.contains(placeId))
            {
                PlaceVote vote = new PlaceVote();
                vote.setPlaceId(placeId);
                vote.setTrip(trip);
                vote.setUserId(user.getUserId());
                placeVoteRepository.save(vote);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    private void selectPlace(User user, Trip trip, Long placeId, Boolean selected)
    {
        Boolean alreadySelected = placeVoteRepository.findByUserIdAndPlaceId(user.getUserId(), placeId) != null;

        if (selected ^ alreadySelected)
        {
            if (selected)
            {
                PlaceVote vote = new PlaceVote();
                vote.setTrip(trip);
                vote.setPlaceId(placeId);
                vote.setUserId(user.getUserId());
                placeVoteRepository.save(vote);
            }
            else
            {
                placeVoteRepository.deleteByUserIdAndPlaceId(user.getUserId(), placeId);
            }
        }
    }
}
