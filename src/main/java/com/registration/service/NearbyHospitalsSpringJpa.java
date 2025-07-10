package com.registration.service;

import com.registration.entity.placefinder.ClimateZone;
import com.registration.entity.placefinder.NearbyHospitals;
import com.registration.ex.climatezoneexception.ClimateZoneApiException;
import com.registration.ex.climatezoneexception.ClimateZoneNotFoundException;
import com.registration.ex.nearbyhospitalsexception.NearbyHospitalsApiException;
import com.registration.ex.nearbyhospitalsexception.NearbyHospitalsNotFoundException;
import com.registration.repository.ClimateZoneRepository;
import com.registration.repository.NearbyHospitalsRepository;
import com.registration.request.ClimateZoneRequest;
import com.registration.request.NearbyHospitalsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NearbyHospitalsSpringJpa implements NearbyHospitalsService{

    @Autowired
    private NearbyHospitalsRepository nearbyHospitalsRepository;


    @Override
    public List<NearbyHospitalsRequest> getNearbyHospitals() {
        try {
            return nearbyHospitalsRepository.findAll()
                    .stream()
                    .map(NearbyHospitals::toNearbyHospitalsRequest)
                    .toList();
        } catch (Exception e) {
            throw new NearbyHospitalsApiException("Problem during getting all nearby hospitals", e);
        }
    }


    @Override
    public NearbyHospitalsRequest getNearbyHospitalById(UUID id) {
        NearbyHospitals nearbyHospitals = nearbyHospitalsRepository.findById(id)
                .orElseThrow(() -> new NearbyHospitalsNotFoundException("Nearby hospital not found with given ID"));
        return nearbyHospitals.toNearbyHospitalsRequest();
    }
}
