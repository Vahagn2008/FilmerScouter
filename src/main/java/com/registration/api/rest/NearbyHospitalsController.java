package com.registration.api.rest;

import com.registration.constants.RoutConstants;
import com.registration.request.CountryRequest;
import com.registration.request.NearbyHospitalsRequest;
import com.registration.service.CountryService;
import com.registration.service.NearbyHospitalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(RoutConstants.BASE_URL + "${platform.version}" + RoutConstants.NEARBY_HOSPITALS)
public class NearbyHospitalsController {

    @Autowired
    private NearbyHospitalsService nearbyHospitalsService;

    @GetMapping
    public List<NearbyHospitalsRequest> getNearbyHospitals() {
        return nearbyHospitalsService.getNearbyHospitals();
    }

    @GetMapping("/{id}")
    public NearbyHospitalsRequest getNearbyHospitalById(@PathVariable UUID id) {
        System.out.println("Received ID: " + id);
        return nearbyHospitalsService.getNearbyHospitalById(id);
    }
}
