package com.registration.service;

import com.registration.request.ClimateZoneRequest;
import com.registration.request.NearbyHospitalsRequest;

import java.util.List;
import java.util.UUID;

public interface NearbyHospitalsService {

    List<NearbyHospitalsRequest> getNearbyHospitals();
    NearbyHospitalsRequest getNearbyHospitalById(UUID id);
}
