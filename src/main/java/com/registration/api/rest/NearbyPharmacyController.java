package com.registration.api.rest;

import com.registration.constants.RoutConstants;
import com.registration.request.CountryRequest;
import com.registration.request.NearbyPharmaciesRequest;
import com.registration.service.CountryService;
import com.registration.service.NearbyPharmaciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(RoutConstants.BASE_URL + "${platform.version}" + RoutConstants.NEARBY_PHARMACY)
public class NearbyPharmacyController {

    @Autowired
    private NearbyPharmaciesService nearbyPharmaciesService;

    @GetMapping
    public List<NearbyPharmaciesRequest> getNearbyPharmacies() {
        return nearbyPharmaciesService.getNearbyPharmacies();
    }

    @GetMapping("/{id}")
    public NearbyPharmaciesRequest getNearbyPharmacyById(@PathVariable UUID id) {
        System.out.println("Received ID: " + id);
        return nearbyPharmaciesService.getNearbyPharmacyById(id);
    }
}
