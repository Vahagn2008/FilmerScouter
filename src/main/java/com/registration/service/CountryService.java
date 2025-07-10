package com.registration.service;

import com.registration.request.ClimateZoneRequest;
import com.registration.request.CountryRequest;

import java.util.List;
import java.util.UUID;

public interface CountryService {

    List<CountryRequest> getAllCountries();
    CountryRequest getCountryById(UUID id);
}
