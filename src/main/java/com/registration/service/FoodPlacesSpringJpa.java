package com.registration.service;

import com.registration.entity.placefinder.ClimateZone;
import com.registration.entity.placefinder.FoodPlaces;
import com.registration.ex.climatezoneexception.ClimateZoneApiException;
import com.registration.ex.climatezoneexception.ClimateZoneNotFoundException;
import com.registration.ex.foodplacesexception.FoodPlaceApiException;
import com.registration.ex.foodplacesexception.FoodPlaceNotFoundException;
import com.registration.repository.ClimateZoneRepository;
import com.registration.repository.FoodPlacesRepository;
import com.registration.request.ClimateZoneRequest;
import com.registration.request.FoodPlacesRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FoodPlacesSpringJpa implements FoodPlacesService {

    @Autowired
    private FoodPlacesRepository foodPlacesRepository;


    @Override
    public List<FoodPlacesRequest> getFoodPlaces() {
        try {
            return foodPlacesRepository.findAll()
                    .stream()
                    .map(FoodPlaces::toFoodPlacesRequest)
                    .toList();
        } catch (Exception e) {
            throw new FoodPlaceApiException("Problem during getting all food places", e);
        }
    }


    @Override
    public FoodPlacesRequest getFoodPlaceById(UUID id) {
        FoodPlaces foodPlaces = foodPlacesRepository.findById(id)
                .orElseThrow(() -> new FoodPlaceNotFoundException("Food place not found with given ID"));
        return foodPlaces.toFoodPlacesRequest();
    }
}
