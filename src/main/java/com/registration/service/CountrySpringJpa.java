package com.registration.service;

import com.registration.entity.placefinder.CountryEntity;
import com.registration.entity.placefinder.PlaceType;
import com.registration.ex.countryexception.CountryApiException;
import com.registration.ex.placeexceptions.PlaceNotFoundException;
import com.registration.repository.CountryRepository;
import com.registration.request.CountryRequest;
import com.registration.request.PlaceTypeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CountrySpringJpa implements CountryService{

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public List<CountryRequest> getAllCountries() {
        try {
            return countryRepository.findAll()
                    .stream()
                    .map(com.registration.entity.placefinder.CountryEntity::toCountryRequest)
                    .toList();
        } catch (Exception e) {
            throw new CountryApiException("Problem during getting all countries", e);
        }
    }


    @Override
    public CountryRequest getCountryById(UUID id) {
        CountryEntity countryEntity = countryRepository.findById(id)
                .orElseThrow(() -> new CountryApiException("Country not found with given ID"));
        return countryEntity.toCountryRequest();
    }


}
