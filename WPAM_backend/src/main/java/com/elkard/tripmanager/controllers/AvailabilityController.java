package com.elkard.tripmanager.controllers;

import com.elkard.tripmanager.dto.requests.availability.SubmitAvailabilityRequest;
import com.elkard.tripmanager.dto.responses.BaseResponse;
import com.elkard.tripmanager.services.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/date")
public class AvailabilityController
{
    @Autowired
    private AvailabilityService availabilityService;

    @PutMapping("/submitDates")
    public BaseResponse submitDate(@RequestBody SubmitAvailabilityRequest request)
    {
        return availabilityService.submitAvailability(request);
    }
}