package com.registration.api.rest;

import com.registration.constants.RoutConstants;
import com.registration.request.CountryRequest;
import com.registration.request.GasStationsRequest;
import com.registration.service.CountryService;
import com.registration.service.GasStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(RoutConstants.BASE_URL + "${platform.version}" + RoutConstants.GAS_STATION)
public class GasStationController {

    @Autowired
    private GasStationService gasStationService;

    @GetMapping
    public List<GasStationsRequest> getGasStations() {
        return gasStationService.getGasStations();
    }

    @GetMapping("/{id}")
    public GasStationsRequest getGasStationById(@PathVariable UUID id) {
        System.out.println("Received ID: " + id);
        return gasStationService.getGasStationById(id);
    }
}
