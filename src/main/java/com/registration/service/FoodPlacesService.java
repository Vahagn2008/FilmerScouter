package com.registration.service;

import com.registration.request.ClimateZoneRequest;
import com.registration.request.FoodPlacesRequest;

import java.util.List;
import java.util.UUID;

public interface FoodPlacesService {

    List<FoodPlacesRequest> getFoodPlaces();
    FoodPlacesRequest getFoodPlaceById(UUID id);
}
