package com.elkard.tripmanager.services;

import com.elkard.tripmanager.dto.requests.trip.*;
import com.elkard.tripmanager.dto.responses.trip.*;
import com.elkard.tripmanager.dto.PlaceDetails;
import com.elkard.tripmanager.exceptions.*;
import com.elkard.tripmanager.model.dao.*;
import com.elkard.tripmanager.model.entities.*;
import com.elkard.tripmanager.dto.responses.BaseResponse;
import com.elkard.tripmanager.model.compositekeys.UsersTripCompositeKey;
import com.elkard.tripmanager.utils.DateUtils;
import com.elkard.tripmanager.utils.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TripService 
{
    private static final Logger log = LogManager.getLogger(TripService.class);

    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private UsersTripRepository usersTripRepository;
    @Autowired
    private AvailabilityRepository availabilityRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private AnswerVoteRepository answerVoteRepository;
    @Autowired
    private PlaceVoteRepository placeVoteRepository;
    @Autowired
    private TripDateRepository tripDateRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private PlaceDetailsService placeDetailsService;
    @Autowired
    private QuestionDetailsService questionDetailsService;
    @Autowired
    private AvailabilityService availabilityService;
    @Autowired
    private AccessService accessService;
    @Autowired
    private TripDateService tripDateService;

    public CreateTripResponse createTrip(CreateTripRequest request)
    {
        log.info("START createTrip");

        CreateTripResponse response = new CreateTripResponse();

        try
        {
            User user = userService.getOrCreateUserFromRequest(request);
            String code = createAndSaveTrip(user, request);
            response.setTripCode(code);

            log.info("SUCCESS createTrip");
        }
        catch (Exception e)
        {
            response.setErrorCode("-1");
            response.setErrorDesc(e.getMessage());
            log.error("ERROR createTrip: " + e.getMessage(), e);
        }

        return response;
    }
    
    public JoinTripResponse joinTrip(JoinTripRequest request)
    {
        log.info("START joinTrip");

        JoinTripResponse response = new JoinTripResponse();

        try
        {
            User user = userService.getOrCreateUserFromRequest(request);
            Trip joinedTrip = joinTripByCode(user, request.getNickname(), request.getCode());
            UsersTrip host = usersTripRepository.findByUserIdAndTripId(joinedTrip.getHost().getUserId(), joinedTrip.getTripId());
            response.setTripName(joinedTrip.getName());
            response.setHost(host.getUsername());

            log.info("SUCCESS joinTrip");
        }
        catch (Exception e)
        {
            response.setErrorCode("-1");
            response.setErrorDesc(e.getMessage());
            log.error("ERROR joinTrip: " + e.getMessage(), e);
        }

        return response;
    }
    
    public BaseResponse deleteTrip(DeleteTripRequest request)
    {
        log.info("START deleteTrip");

        BaseResponse response = new BaseResponse();

        try
        {
            User user = userService.getUserFromRequest(request);
            Trip trip = tripRepository.getOne(request.getTripId());

            //TODO check if user should have permission to delete this trip
            accessService.checkAccessToTrip(user, trip);

            deleteTrip(trip.getTripId());

            log.info("SUCCESS deleteTrip");
        }
        catch (Exception e)
        {
            response.setErrorCode("-1");
            response.setErrorDesc(e.getMessage());
            log.error("ERROR deleteTrip: " + e.getMessage(), e);
        }

        return response;
    }
    
    public BaseResponse leaveTrip(LeaveTripRequest request)
    {
        log.info("START leaveTrip");

        BaseResponse response = new BaseResponse();

        try
        {
            User user = userService.getUserFromRequest(request);
            Trip trip = tripRepository.getOne(request.getTripId());

            accessService.checkAccessToTrip(user, trip);
            //TODO block leaving trip by its host
            if (trip.getHost().getUserId().equals(user.getUserId()))
            {
                throw new WrongUserException("Host cannot leave their trip");
            }

            leaveTripByUser(trip, user);

            log.info("SUCCESS leaveTrip");
        }
        catch (Exception e)
        {
            response.setErrorCode("-1");
            response.setErrorDesc(e.getMessage());
            log.error("ERROR leaveTrip: " + e.getMessage(), e);
        }

        return response;
    }

    public BaseResponse acceptTrip(AcceptTripRequest request)
    {
        log.info("START acceptTrip");

        BaseResponse response = new BaseResponse();

        try
        {
            User user = userService.getUserFromRequest(request);
            Trip trip = tripRepository.getOne(request.getTripId());

            accessService.checkAccessToTrip(user, trip);

            //Place place = placeRepository.getOne(request.getPlaceId());
            List<Place> places = placeRepository.findByTripTripIdAndUserUserId(trip.getTripId(), user.getUserId());

            //TODO check if place belongs to found trip

            confirmTrip(trip, user, places, request.getDays());

            log.info("SUCCESS acceptTrip");
        }
        catch (Exception e)
        {
            response.setErrorCode("-1");
            response.setErrorDesc(e.getMessage());
            log.error("ERROR acceptTrip: " + e.getMessage(), e);
        }

        return response;
    }
    
    public GetTripListResponse getTripList(String deviceId)
    {
        log.info("START getTripList");

        GetTripListResponse response = new GetTripListResponse();

        try
        {
            List<TripListItem> result = new ArrayList<>();
            User user = userService.getUserByDeviceId(deviceId);

            List<UsersTrip> trips = usersTripRepository.findByUserId(user.getUserId());

            for (UsersTrip trip : trips)
            {
                result.add(getGeneralTripInfo(tripRepository.getOne(trip.getTripId()), user));
            }

            response.setTrips(result);

            log.info("SUCCESS getTripList");
        }
        catch (Exception e)
        {
            response.setErrorCode("-1");
            response.setErrorDesc(e.getMessage());
            log.error("ERROR getTripList: " + e.getMessage(), e);
        }

        return response;
    }

    public GetTripDetailsResponse getTripDetails(String deviceId, Long tripId)
    {
        log.info("START getTripDetails");

        GetTripDetailsResponse response = new GetTripDetailsResponse();

        try
        {
            User user = userService.getUserByDeviceId(deviceId);
            Trip trip = tripRepository.getOne(tripId);

            accessService.checkAccessToTrip(user, trip);

            response.setGeneral(getGeneralTripInfo(trip, user));
            response.setPlaces(placeDetailsService.getPlaces(trip, user));
            response.setQuestions(questionDetailsService.getQuestions(trip, user));
            response.setDays(tripDateService.getTripDates(trip));
            response.setAvailability(availabilityService.getTripAvailability(trip, user));
            response.setAllowAddingPlaces(trip.getAllowAddingPlaces());
            response.setAllowAddingQuestions(trip.getAllowAddingQuestions());
            response.setAllowInviting(trip.getAllowInviting());

            List<PlaceVote> allVotes = placeVoteRepository.findByTripTripId(trip.getTripId());
            Set<Long> uniqueVoters = new HashSet<>();
            for (PlaceVote vote : allVotes)
                uniqueVoters.add(vote.getUserId());
            response.setPlaceAllVotes(Long.valueOf(uniqueVoters.size()));

            response.setIsHost(trip.getHost().getUserId().equals(user.getUserId()));
            response.setDateVotes(availabilityService.getDateVotes(trip));
            response.setTripCode(trip.getCode());

            List<UsersTrip> allNicknames = usersTripRepository.findByTripId(trip.getTripId());
            List<NicknameItem> nicknames = new ArrayList<>();
            Integer id = 0;
            for (UsersTrip usersTrip : allNicknames)
            {
                NicknameItem item = new NicknameItem();
                item.setId(id.toString());
                id++;
                item.setNickname(usersTrip.getUsername());
                item.setCurUser(usersTrip.getUserId().equals(user.getUserId()));
                item.setHost(usersTrip.getUserId().equals(trip.getHost().getUserId()));
                nicknames.add(item);
            }
            response.setNicknames(nicknames);

            log.info("SUCCESS getTripDetails");
        }
        catch (Exception e)
        {
            response.setErrorCode("-1");
            response.setErrorDesc(e.getMessage());
            log.error("ERROR getTripDetails: " + e.getMessage(), e);
        }

        return response;
    }

    @Transactional(rollbackFor = Exception.class)
    public String createAndSaveTrip(User user, CreateTripRequest request) throws RequestParamsException
    {
        String code = generateTripCode();

        Trip trip = new Trip();
        trip.setName(request.getTripName());
        trip.setHost(user);
        trip.setAllowAddingPlaces(request.getAllowAddingPlaces());
        trip.setAllowAddingQuestions(request.getAllowAddingQuestions());
        trip.setAllowInviting(request.getAllowInviting());
        trip.setAccepted(false);
        trip.setCode(code);
        trip.setAcceptedPlace(null);
        tripRepository.save(trip);

        UsersTrip usersTrip = new UsersTrip();
        usersTrip.setUserId(user.getUserId());
        usersTrip.setTripId(trip.getTripId());
        usersTrip.setUsername(request.getNickname());
        usersTripRepository.save(usersTrip);

        for (String date : request.getDays())
        {
            TripDate day = new TripDate();
            day.setTripId(trip.getTripId());
            day.setDay(date);
            tripDateRepository.save(day);
        }

        for (PlaceDetails placeDetails : request.getPlaces())
        {
            Place place = new Place();
            place.setTrip(trip);
            place.setName(placeDetails.getAddressName());
            place.setUser(user);
            place.setAccepted(false);
            if (placeDetails.getContainsCoords())
            {
                if (placeDetails.getLat() == null || placeDetails.getLon() == null)
                    throw new RequestParamsException("ContainsCoord is true but there is no coords");

                place.setLatitude(placeDetails.getLat());
                place.setLongitude(placeDetails.getLon());
            }
            placeRepository.save(place);
        }

        if (request.getPrevTripId() != null)
        {
            Trip prevTrip = tripRepository.getOne(request.getPrevTripId());
            List<UsersTrip> participants = usersTripRepository.findByTripId(prevTrip.getTripId());
            for (UsersTrip participant : participants)
            {
                if (participant.getUserId().equals(user.getUserId()))
                    continue;

                UsersTrip newParticipant = new UsersTrip();
                newParticipant.setTripId(trip.getTripId());
                newParticipant.setUserId(participant.getUserId());
                newParticipant.setUsername(participant.getUsername());
                usersTripRepository.save(newParticipant);
            }
        }

        return code;
    }

    private String generateTripCode()
    {
        for (int i = 0; i < 10; i++)
        {
            String code = RandomUtils.alphanumericString(7);
            Trip trip = tripRepository.findByCode(code);
            if (trip == null)
                return code;
        }

        throw new UnexpectedException("Cannot generate unique trip's code");
    }

    @Transactional(rollbackFor = Exception.class)
    private Trip joinTripByCode(User user, String nickname, String code) throws TripNotFoundException, AlreadyJoinedException
    {
        Trip trip = tripRepository.findByCode(code);
        if (trip == null)
        {
            throw new TripNotFoundException();
        }
        UsersTrip checkIfAlreadyJoined = usersTripRepository.findByUserIdAndTripId(user.getUserId(), trip.getTripId());
        if (checkIfAlreadyJoined != null)
        {
            throw new AlreadyJoinedException(trip.getName());
        }

        UsersTrip usersTrip = new UsersTrip();
        usersTrip.setTripId(trip.getTripId());
        usersTrip.setUserId(user.getUserId());
        usersTrip.setUsername(nickname);
        usersTripRepository.save(usersTrip);

        return trip;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private void leaveTripByUser(Trip trip, User user)
    {
        answerVoteRepository.deleteByUserIdAndTripTripId(user.getUserId(), trip.getTripId());
        placeVoteRepository.deleteByUserIdAndTripTripId(user.getUserId(), trip.getTripId());
        availabilityRepository.deleteByUserIdAndTripId(user.getUserId(), trip.getTripId());
        usersTripRepository.deleteByUserIdAndTripId(user.getUserId(), trip.getTripId());
    }

    @Transactional(rollbackFor = Exception.class)
    private void confirmTrip(Trip trip, User user, List<Place> places, List<String> days)
    {
        //trip.setAcceptedPlace(place);
        //availabilityRepository.deleteByTripId(trip.getTripId());
        tripDateRepository.deleteByTripId(trip.getTripId());
        for (String date : days)
        {
            TripDate day = new TripDate();
            day.setTripId(trip.getTripId());
            day.setDay(date);
            tripDateRepository.save(day);
        }

        for (Place place : places)
        {
            place.setAccepted(true);
            placeRepository.save(place);
        }

        trip.setAccepted(true);
        tripRepository.save(trip);
    }

    private TripListItem getGeneralTripInfo(Trip trip, User curUser)
    {
        TripListItem general = new TripListItem();
        general.setTripId(trip.getTripId());
        general.setTripName(trip.getName());
        general.setDateRange(getDateRange(trip));
        general.setPlaceSummary(getPlaceSummary(trip));
        general.setHostNickname(getHostNickname(trip));
        general.setAccepted(trip.getAccepted());
        general.setPast(isTripPast(trip));
        general.setNotifications(0);
        general.setOwner(trip.getHost().getUserId().equals(curUser.getUserId()));
        general.setNickname(getUserNicknameInTrip(trip, curUser));

        return general;
    }

    private String getDateRange(Trip trip)
    {
        List<TripDate> days = tripDateRepository.findByTripIdOrderByDay(trip.getTripId());

        if (days.size() == 0)
            return "No time";

        if (days.size() == 1)
            return DateUtils.formatDate(days.get(0).getDay());

        return DateUtils.formatDateRange(days.get(0).getDay(), days.get(days.size()-1).getDay());
    }

    private String getPlaceSummary(Trip trip)
    {
        if (!trip.getAccepted())
        {
            List<Place> places = placeRepository.findByTripTripId(trip.getTripId());

            if (places.size() == 0)
                return "No place";

            if (places.size() == 1)
                return places.get(0).getName();

            return "Many places";
        }
        else
        {
            List<PlaceVote> places = placeVoteRepository.findByUserIdAndTripTripId(trip.getHost().getUserId(), trip.getTripId());

            if (places.size() == 0)
                return "No place";

            if (places.size() == 1)
            {
                return placeRepository.getOne(places.get(0).getPlaceId()).getName();
            }

            return "Many places";
        }
    }

    private String getHostNickname(Trip trip)
    {
        UsersTrip usersTrip = usersTripRepository.getOne(new UsersTripCompositeKey(trip));
        return usersTrip.getUsername();
    }

    private String getUserNicknameInTrip(Trip trip, User user)
    {
        UsersTrip usersTrip = usersTripRepository.findByUserIdAndTripId(user.getUserId(), trip.getTripId());
        return usersTrip.getUsername();
    }

    private boolean isTripPast(Trip trip)
    {
        if (!trip.getAccepted())
            return false;

        List<TripDate> days = tripDateRepository.findByTripIdOrderByDay(trip.getTripId());

        //TODO compare last day with sysdate
        return false;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private void deleteTrip(Long tripId)
    {
        answerVoteRepository.deleteByTripTripId(tripId);
        answerRepository.deleteByTripTripId(tripId);
        questionRepository.deleteByTripTripId(tripId);
        placeVoteRepository.deleteByTripTripId(tripId);
        placeRepository.deleteByTripTripId(tripId);
        usersTripRepository.deleteByTripId(tripId);
        tripDateRepository.deleteByTripId(tripId);
        availabilityRepository.deleteByTripId(tripId);
        tripRepository.deleteByTripId(tripId);
    }
}
