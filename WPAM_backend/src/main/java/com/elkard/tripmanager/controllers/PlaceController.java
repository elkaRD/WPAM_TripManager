package com.elkard.tripmanager.controllers;

import com.elkard.tripmanager.dto.requests.place.AddPlaceRequest;
import com.elkard.tripmanager.dto.requests.place.SelectPlaceRequest;
import com.elkard.tripmanager.dto.requests.place.SubmitPlaceRequest;
import com.elkard.tripmanager.services.PlaceService;
import com.elkard.tripmanager.dto.responses.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/place")
public class PlaceController
{
    @Autowired
    private PlaceService placeService;

    @PostMapping("/addPlace")
    public BaseResponse addPlace(@RequestBody AddPlaceRequest request)
    {
        return placeService.addPlace(request);
    }

    @PutMapping("/submitPlace")
    public BaseResponse submitPlace(@RequestBody SubmitPlaceRequest request)
    {
        return placeService.submitPlace(request);
    }

    @PutMapping("/selectPlace")
    public BaseResponse selectPlace(@RequestBody SelectPlaceRequest request)
    {
        return placeService.selectPlace(request);
    }
}
