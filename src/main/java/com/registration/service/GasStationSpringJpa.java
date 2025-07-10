package com.registration.service;

import com.registration.entity.placefinder.ClimateZone;
import com.registration.entity.placefinder.GasStation;
import com.registration.ex.climatezoneexception.ClimateZoneApiException;
import com.registration.ex.climatezoneexception.ClimateZoneNotFoundException;
import com.registration.ex.gasstationexception.GasStationApiException;
import com.registration.ex.gasstationexception.GasStationNotFoundException;
import com.registration.repository.ClimateZoneRepository;
import com.registration.repository.GasStationsRepository;
import com.registration.request.ClimateZoneRequest;
import com.registration.request.GasStationsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GasStationSpringJpa implements GasStationService {

    @Autowired
    private GasStationsRepository gasStationsRepository;


    @Override
    public List<GasStationsRequest> getGasStations() {
        try {
            return gasStationsRepository.findAll()
                    .stream()
                    .map(GasStation::toGasStationRequest)
                    .toList();
        } catch (Exception e) {
            throw new GasStationApiException("Problem during getting all gas stations", e);
        }
    }


    @Override
    public GasStationsRequest getGasStationById(UUID id) {
        GasStation gasStation = gasStationsRepository.findById(id)
                .orElseThrow(() -> new GasStationNotFoundException("Gas station not found with given ID"));
        return gasStation.toGasStationRequest();
    }
}
