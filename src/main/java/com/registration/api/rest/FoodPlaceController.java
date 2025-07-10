package com.registration.api.rest;

import com.registration.constants.RoutConstants;
import com.registration.request.CountryRequest;
import com.registration.request.FoodPlacesRequest;
import com.registration.service.CountryService;
import com.registration.service.FoodPlacesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(RoutConstants.BASE_URL + "${platform.version}" + RoutConstants.FOOD_PLACES)
public class FoodPlaceController {

    @Autowired
    private FoodPlacesService foodPlacesService;

    @GetMapping
    public List<FoodPlacesRequest> getFoodPlaces() {
        return foodPlacesService.getFoodPlaces();
    }

    @GetMapping("/{id}")
    public FoodPlacesRequest getFoodPlaceById(@PathVariable UUID id) {
        System.out.println("Received ID: " + id);
        return foodPlacesService.getFoodPlaceById(id);
    }
}
