package com.registration.service;

import com.registration.entity.placefinder.PlaceType;
import com.registration.entity.placefinder.TransportType;
import com.registration.ex.placeexceptions.PlaceNotFoundException;
import com.registration.ex.transporttypeexcpetion.TransportTypeApiException;
import com.registration.ex.transporttypeexcpetion.TransportTypeNotFoundException;
import com.registration.repository.TransportTypeRepository;
import com.registration.request.PlaceTypeRequest;
import com.registration.request.TransportTypeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransportTypeSpringJpa implements TransportTypeService{

    @Autowired
    private TransportTypeRepository transportTypeRepository;


    @Override
    public List<TransportTypeRequest> getAllTransportTypes() {
        try {
            return transportTypeRepository.findAll()
                    .stream()
                    .map(TransportType::toTransportTypeRequest)
                    .toList();
        } catch (Exception e) {
            throw new TransportTypeApiException("Problem during getting all transports", e);
        }
    }


    @Override
    public TransportTypeRequest getTransportTypeById(UUID id) {
        TransportType transportType = transportTypeRepository.findById(id)
                .orElseThrow(() -> new TransportTypeNotFoundException("Transport type not found with given ID"));
        return transportType.toTransportTypeRequest();
    }
}
