package com.registration.service;

import com.registration.request.PlaceTypeRequest;

import java.util.List;
import java.util.UUID;

public interface PlaceTypeService {

    List<PlaceTypeRequest> getAllPlaceTypes();
    PlaceTypeRequest getPlaceTypeById(UUID id);
}
