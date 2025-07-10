package com.registration.api.rest;


import com.registration.constants.RoutConstants;
import com.registration.request.PlaceRequest;
import com.registration.request.PlaceTypeRequest;
import com.registration.service.PlaceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(RoutConstants.BASE_URL + "${platform.version}" + RoutConstants.PLACE_TYPES)
public class PlaceTypeController {

    @Autowired
    private PlaceTypeService placeTypeService;


    @GetMapping
    public List<PlaceTypeRequest> getAllPlaceTypes() {
        return placeTypeService.getAllPlaceTypes();
    }

    @GetMapping("/{id}")
    public PlaceTypeRequest getPlaceTypeById(@PathVariable UUID id) {
        System.out.println("Received ID: " + id);
        return placeTypeService.getPlaceTypeById(id);
    }
}
