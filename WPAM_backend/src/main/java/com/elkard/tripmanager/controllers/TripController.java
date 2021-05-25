package com.elkard.tripmanager.controllers;

import com.elkard.tripmanager.dto.requests.trip.*;
import com.elkard.tripmanager.dto.responses.BaseResponse;
import com.elkard.tripmanager.dto.responses.trip.CreateTripResponse;
import com.elkard.tripmanager.dto.responses.trip.GetTripDetailsResponse;
import com.elkard.tripmanager.dto.responses.trip.GetTripListResponse;
import com.elkard.tripmanager.dto.responses.trip.JoinTripResponse;
import com.elkard.tripmanager.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/trip")
public class TripController {

    @Autowired
    private TripService tripService;

    @PostMapping("/createTrip")
    public CreateTripResponse createTrip(@RequestBody CreateTripRequest request)
    {
        return tripService.createTrip(request);
    }

    @PutMapping("/joinTrip")
    public JoinTripResponse joinTrip(@RequestBody JoinTripRequest request)
    {
        return tripService.joinTrip(request);
    }

    @DeleteMapping("/deleteTrip")
    public BaseResponse deleteTrip(@RequestBody DeleteTripRequest request)
    {
        return tripService.deleteTrip(request);
    }

    @PutMapping("/leaveTrip")
    public BaseResponse leaveTrip(@RequestBody LeaveTripRequest request)
    {
        return tripService.leaveTrip(request);
    }

    @PutMapping("/acceptTrip")
    public BaseResponse acceptTrip(@RequestBody AcceptTripRequest request)
    {
        return tripService.acceptTrip(request);
    }

    @GetMapping("/getTripList")
    public GetTripListResponse getTripList(@RequestParam String deviceId)
    {
        return tripService.getTripList(deviceId);
    }

    @GetMapping("/getTripDetails")
    public GetTripDetailsResponse getTripDetails(@RequestParam String deviceId, @RequestParam Long tripId)
    {
        return tripService.getTripDetails(deviceId, tripId);
    }
}
