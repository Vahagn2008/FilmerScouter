package com.registration.service;

import com.registration.entity.placefinder.PlaceType;
import com.registration.ex.placeexceptions.PlaceNotFoundException;
import com.registration.ex.placetypeexception.PlaceTypeApiException;
import com.registration.repository.PlaceTypeRepository;
import com.registration.request.PlaceTypeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PlaceTypeSpringJpa implements PlaceTypeService {


    @Autowired
    private PlaceTypeRepository placeTypeRepository;


    @Override
    public List<PlaceTypeRequest> getAllPlaceTypes() {
        try {
            return placeTypeRepository.findAll()
                    .stream()
                    .map(com.registration.entity.placefinder.PlaceType::toPlaceTypeRequest)
                    .toList();
        } catch (Exception e) {
            throw new PlaceTypeApiException("Problem during getting all places", e);
        }
    }

    @Override
    public PlaceTypeRequest getPlaceTypeById(UUID id) {
        PlaceType placeEntity = placeTypeRepository.findById(id)
                .orElseThrow(() -> new PlaceNotFoundException("Place not found with given ID"));
        return placeEntity.toPlaceTypeRequest();
    }
}
