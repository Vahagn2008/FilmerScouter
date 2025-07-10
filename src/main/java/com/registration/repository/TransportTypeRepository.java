package com.registration.repository;

import com.registration.entity.placefinder.TransportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransportTypeRepository extends JpaRepository<TransportType, UUID> {
}
