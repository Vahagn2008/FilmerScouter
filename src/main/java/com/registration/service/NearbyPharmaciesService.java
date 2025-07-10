package com.registration.service;

import com.registration.request.ClimateZoneRequest;
import com.registration.request.NearbyPharmaciesRequest;

import java.util.List;
import java.util.UUID;

public interface NearbyPharmaciesService {

    List<NearbyPharmaciesRequest> getNearbyPharmacies();
    NearbyPharmaciesRequest getNearbyPharmacyById(UUID id);
}
