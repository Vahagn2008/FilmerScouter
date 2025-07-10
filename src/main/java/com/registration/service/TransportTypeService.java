package com.registration.service;

import com.registration.request.TransportTypeRequest;

import java.util.List;
import java.util.UUID;

public interface TransportTypeService {

    List<TransportTypeRequest> getAllTransportTypes();
    TransportTypeRequest getTransportTypeById(UUID id);

}
