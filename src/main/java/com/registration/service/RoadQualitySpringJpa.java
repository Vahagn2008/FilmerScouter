package com.registration.service;

import com.registration.entity.placefinder.PlaceType;
import com.registration.entity.placefinder.RoadQuality;
import com.registration.ex.placeexceptions.PlaceNotFoundException;
import com.registration.ex.roadqualityexception.RoadQualityApiException;
import com.registration.ex.roadqualityexception.RoadQualityNotFoundException;
import com.registration.repository.RoadQualityRepository;
import com.registration.request.PlaceTypeRequest;
import com.registration.request.RoadQualityRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RoadQualitySpringJpa implements RoadQualityService{


    @Autowired
    private RoadQualityRepository roadQualityRepository;


    @Override
    public List<RoadQualityRequest> getAllRoadQualities() {
        try {
            return roadQualityRepository.findAll()
                    .stream()
                    .map(RoadQuality::toRoadQualityRequest)
                    .toList();
        } catch (Exception e) {
            throw new RoadQualityApiException("Problem during getting all road qualities", e);
        }
    }


    @Override
    public RoadQualityRequest getRoadQualityById(UUID id) {
        RoadQuality roadQuality = roadQualityRepository.findById(id)
                .orElseThrow(() -> new RoadQualityNotFoundException("Road Quality not found with given ID"));
        return roadQuality.toRoadQualityRequest();
    }

}
