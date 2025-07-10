package com.registration.service;

import com.registration.entity.placefinder.ClimateZone;
import com.registration.entity.placefinder.NearbyHotels;
import com.registration.ex.climatezoneexception.ClimateZoneApiException;
import com.registration.ex.climatezoneexception.ClimateZoneNotFoundException;
import com.registration.ex.nearbyhotelsexception.NearbyHotelsApiException;
import com.registration.ex.nearbyhotelsexception.NearbyHotelsNotFoundException;
import com.registration.repository.ClimateZoneRepository;
import com.registration.repository.NearbyHotelsRepository;
import com.registration.request.ClimateZoneRequest;
import com.registration.request.NearbyHotelsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NearbyHotelsSpringJpa implements NearbyHotelsService{

    @Autowired
    private NearbyHotelsRepository nearbyHotelsRepository;


    @Override
    public List<NearbyHotelsRequest> getNearbyHotels() {
        try {
            return nearbyHotelsRepository.findAll()
                    .stream()
                    .map(NearbyHotels::toNearbyHotelsRequest)
                    .toList();
        } catch (Exception e) {
            throw new NearbyHotelsApiException("Problem during getting all nearby hotels", e);
        }
    }


    @Override
    public NearbyHotelsRequest getNearbyHotelById(UUID id) {
        NearbyHotels nearbyHotels = nearbyHotelsRepository.findById(id)
                .orElseThrow(() -> new NearbyHotelsNotFoundException("Nearby hotel not found with given ID"));
        return nearbyHotels.toNearbyHotelsRequest();
    }
}
