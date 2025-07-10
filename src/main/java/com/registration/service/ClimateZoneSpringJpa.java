package com.registration.service;

import com.registration.entity.placefinder.ClimateZone;
import com.registration.ex.climatezoneexception.ClimateZoneApiException;
import com.registration.ex.climatezoneexception.ClimateZoneNotFoundException;
import com.registration.ex.placeexceptions.PlaceNotFoundException;
import com.registration.repository.ClimateZoneRepository;
import com.registration.request.ClimateZoneRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClimateZoneSpringJpa implements ClimateZoneService{

    @Autowired
    private ClimateZoneRepository climateZoneRepository;


    @Override
    public List<ClimateZoneRequest> getClimateZones() {
        try {
            return climateZoneRepository.findAll()
                    .stream()
                    .map(ClimateZone::toClimateZoneRequest)
                    .toList();
        } catch (Exception e) {
            throw new ClimateZoneApiException("Problem during getting all climate zones", e);
        }
    }


    @Override
    public ClimateZoneRequest getClimateZoneById(UUID id) {
        ClimateZone climateZone = climateZoneRepository.findById(id)
                .orElseThrow(() -> new ClimateZoneNotFoundException("Climate zone not found with given ID"));
        return climateZone.toClimateZoneRequest();
    }
}
