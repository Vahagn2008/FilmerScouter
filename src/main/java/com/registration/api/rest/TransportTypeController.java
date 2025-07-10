package com.registration.api.rest;

import com.registration.constants.RoutConstants;
import com.registration.request.TransportTypeRequest;
import com.registration.service.TransportTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(RoutConstants.BASE_URL + "${platform.version}" + RoutConstants.TRANSPORT_TYPES)
public class TransportTypeController {

    @Autowired
    private TransportTypeService transportTypeService;

    @GetMapping
    public List<TransportTypeRequest> getAllTransportTypes() {
        return transportTypeService.getAllTransportTypes();
    }

    @GetMapping("/{id}")
    public TransportTypeRequest getTransportTypeById(@PathVariable UUID id) {
        System.out.println("Received ID: " + id);
        return transportTypeService.getTransportTypeById(id);
    }
}
