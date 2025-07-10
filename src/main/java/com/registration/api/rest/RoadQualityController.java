package com.registration.api.rest;

import com.registration.constants.RoutConstants;
import com.registration.request.RoadQualityRequest;
import com.registration.service.RoadQualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(RoutConstants.BASE_URL + "${platform.version}" + RoutConstants.ROAD_QUALITIES)
public class RoadQualityController {

    @Autowired
    private RoadQualityService roadQualityService;

    @GetMapping
    private List<RoadQualityRequest> getAllRoadQualities() {
        return roadQualityService.getAllRoadQualities();
    }

    @GetMapping("/{id}")
    public RoadQualityRequest getRoadQualityById(@PathVariable UUID id) {
        System.out.println("Received ID: " + id);
        return roadQualityService.getRoadQualityById(id);
    }
}
