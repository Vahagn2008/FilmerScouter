package com.registration.service;

import com.registration.request.RoadQualityRequest;

import java.util.List;
import java.util.UUID;

public interface RoadQualityService {

    List<RoadQualityRequest> getAllRoadQualities();
    RoadQualityRequest getRoadQualityById(UUID id);
}
