package com.registration.api.rest;

import com.registration.constants.RoutConstants;
import com.registration.request.ClimateZoneRequest;
import com.registration.service.ClimateZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(RoutConstants.BASE_URL + "${platform.version}" + RoutConstants.CLIMATE_ZONES)
public class ClimateZoneController {

    @Autowired
    private ClimateZoneService climateZoneService;

    @GetMapping
    public List<ClimateZoneRequest> getAllClimateZones() {
        return climateZoneService.getClimateZones();
    }

    @GetMapping("/{id}")
    public ClimateZoneRequest getClimatezoneById(@PathVariable UUID id) {
        System.out.println("Received ID: " + id);
        return climateZoneService.getClimateZoneById(id);
    }
}
