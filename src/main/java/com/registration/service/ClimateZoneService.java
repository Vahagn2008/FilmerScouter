package com.registration.service;

import com.registration.request.ClimateZoneRequest;

import java.util.List;
import java.util.UUID;

public interface ClimateZoneService {

    List<ClimateZoneRequest> getClimateZones();
    ClimateZoneRequest getClimateZoneById(UUID id);
}
