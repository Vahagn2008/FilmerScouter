package com.registration.service;

import com.registration.request.ClimateZoneRequest;
import com.registration.request.GasStationsRequest;

import java.util.List;
import java.util.UUID;

public interface GasStationService {

    List<GasStationsRequest> getGasStations();
    GasStationsRequest getGasStationById(UUID id);
}
