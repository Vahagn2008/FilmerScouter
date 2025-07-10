package com.registration.service;

import com.registration.entity.placefinder.ClimateZone;
import com.registration.entity.placefinder.NearbyPharmacy;
import com.registration.ex.climatezoneexception.ClimateZoneApiException;
import com.registration.ex.climatezoneexception.ClimateZoneNotFoundException;
import com.registration.ex.nearbypharmacyexception.NearbyPharmacyApiException;
import com.registration.ex.nearbypharmacyexception.NearbyPharmacyNotFoundException;
import com.registration.repository.ClimateZoneRepository;
import com.registration.repository.NearbyPharmaciesRepository;
import com.registration.request.ClimateZoneRequest;
import com.registration.request.NearbyPharmaciesRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NearbyPharmaciesSpringJpa implements NearbyPharmaciesService{

    @Autowired
    private NearbyPharmaciesRepository nearbyPharmaciesRepository;


    @Override
    public List<NearbyPharmaciesRequest> getNearbyPharmacies() {
        try {
            return nearbyPharmaciesRepository.findAll()
                    .stream()
                    .map(NearbyPharmacy::toNearbyPharmaciesRequest)
                    .toList();
        } catch (Exception e) {
            throw new NearbyPharmacyApiException("Problem during getting all nearby pharmacies", e);
        }
    }


    @Override
    public NearbyPharmaciesRequest getNearbyPharmacyById(UUID id) {
        NearbyPharmacy nearbyPharmacy = nearbyPharmaciesRepository.findById(id)
                .orElseThrow(() -> new NearbyPharmacyNotFoundException("Nearby pharmacy not found with given ID"));
        return nearbyPharmacy.toNearbyPharmaciesRequest();
    }
}
