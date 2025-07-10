package com.registration.service;

import com.registration.request.ClimateZoneRequest;
import com.registration.request.NearbyHotelsRequest;

import java.util.List;
import java.util.UUID;

public interface NearbyHotelsService {

    List<NearbyHotelsRequest> getNearbyHotels();
    NearbyHotelsRequest getNearbyHotelById(UUID id);
}
