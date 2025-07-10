package com.registration.api.rest;

import com.registration.constants.RoutConstants;
import com.registration.request.CountryRequest;
import com.registration.request.NearbyHotelsRequest;
import com.registration.service.CountryService;
import com.registration.service.NearbyHotelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(RoutConstants.BASE_URL + "${platform.version}" + RoutConstants.NEARBY_HOTELS)
public class NearbyHotelsController {

    @Autowired
    private NearbyHotelsService nearbyHotelsService;

    @GetMapping
    public List<NearbyHotelsRequest> getNearbyHotels() {
        return nearbyHotelsService.getNearbyHotels();
    }

    @GetMapping("/{id}")
    public NearbyHotelsRequest getNearbyHotelsById(@PathVariable UUID id) {
        System.out.println("Received ID: " + id);
        return nearbyHotelsService.getNearbyHotelById(id);
    }
}
