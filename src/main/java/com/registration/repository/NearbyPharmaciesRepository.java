package com.registration.repository;

import com.registration.entity.placefinder.NearbyPharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NearbyPharmaciesRepository extends JpaRepository<NearbyPharmacy, UUID> {
    @Query("SELECT nh.id FROM NearbyHotels nh WHERE nh.name = :name")
    UUID findIdByName(@Param("name") String name);
}
