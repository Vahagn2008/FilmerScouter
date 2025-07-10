package com.registration.service;

import com.registration.entity.PlaceEntity;
import com.registration.entity.placefinder.*;
import com.registration.ex.placeexceptions.PlaceApiException;
import com.registration.ex.placeexceptions.PlaceNotFoundException;
import com.registration.repository.*;
import com.registration.request.PlaceRequest;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class PlaceSpringJpa implements PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private PlaceTypeRepository placeTypeRepository;

    @Autowired
    private ClimateZoneRepository climateZoneRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private RoadQualityRepository roadQualityRepository;

    @Autowired
    private NearbyHotelsRepository nearbyHotelsRepository;

    @Autowired
    private NearbyHospitalsRepository nearbyHospitalsRepository;

    @Autowired
    private NearbyPharmaciesRepository nearbyPharmaciesRepository;

    @Autowired
    private GasStationsRepository gasStationsRepository;

    @Autowired
    private FoodPlacesRepository foodPlacesRepository;

    @Autowired
    private Photo360Repository photo360Repository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private TransportTypeRepository transportTypeRepository;

    @Autowired
    private KeywordRepository keywordRepository;


    @Override
    public List<PlaceRequest> getAllPlaces() {
        List<PlaceRequest> requests = new ArrayList<>();
        try {
            List<PlaceEntity> list = placeRepository.findAll()
                    .stream()
                    .toList();

            for (PlaceEntity place : list) {
                PlaceType placeType = safeFindAndUnwrap(placeTypeRepository, place.getPlaceTypeId(), "PlaceType");
                ClimateZone climateZone = safeFindAndUnwrap(climateZoneRepository, place.getClimateZoneId(), "ClimateZone");
                CountryEntity country = safeFindAndUnwrap(countryRepository, place.getCountryId(), "Country");
                Photo360 photo360 = safeFindAndUnwrap(photo360Repository, place.getPhoto360Id(), "Photo360");
                RoadQuality roadQuality = safeFindAndUnwrap(roadQualityRepository, place.getRoadQualityId(), "RoadQuality");
                NearbyHotels nearbyHotels = safeFindAndUnwrap(nearbyHotelsRepository, place.getNearbyHotelsId(), "NearbyHotels");
                NearbyHospitals nearbyHospitals = safeFindAndUnwrap(nearbyHospitalsRepository, place.getNearbyHospitalsId(), "NearbyHospitals");
                NearbyPharmacy nearbyPharmacy = safeFindAndUnwrap(nearbyPharmaciesRepository, place.getNearbyPharmaciesId(), "NearbyPharmacy");
                GasStation gasStation = safeFindAndUnwrap(gasStationsRepository, place.getGasStationsId(), "GasStation");
                FoodPlaces foodPlaces = safeFindAndUnwrap(foodPlacesRepository, place.getFoodPlacesId(), "FoodPlaces");

                List<Photo> photos = safeFindAndConvertToList(photoRepository, place.getPhotosId(), "Photo");
                List<TransportType> transportTypes = safeFindAndConvertToList(transportTypeRepository, place.getTransportTypeId(), "TransportType");

                // Debug print or log field null checks
                log.info("Place ID {} - placeType: {}, climateZone: {}, country: {}, photo360: {}, roadQuality: {}, nearbyHotels: {}, nearbyHospitals: {}, nearbyPharmacy: {}, gasStation: {}, foodPlaces: {}, photos: {}, transportTypes: {}, keywords: {}",
                        place.getId(),
                        placeType == null ? "null" : "OK",
                        climateZone == null ? "null" : "OK",
                        country == null ? "null" : "OK",
                        photo360 == null ? "null" : "OK",
                        roadQuality == null ? "null" : "OK",
                        nearbyHotels == null ? "null" : "OK",
                        nearbyHospitals == null ? "null" : "OK",
                        nearbyPharmacy == null ? "null" : "OK",
                        gasStation == null ? "null" : "OK",
                        foodPlaces == null ? "null" : "OK",
                        (photos == null || photos.isEmpty()) ? "null" : "OK",
                        (transportTypes == null || transportTypes.isEmpty()) ? "null" : "OK"
                );

                PlaceRequest request = place.toPlaceRequest(
                        Optional.ofNullable(placeType),
                        Optional.ofNullable(climateZone),
                        Optional.ofNullable(country),
                        Optional.ofNullable(photo360),
                        Optional.ofNullable(roadQuality),
                        Optional.ofNullable(nearbyHotels),
                        Optional.ofNullable(nearbyHospitals),
                        Optional.ofNullable(nearbyPharmacy),
                        Optional.ofNullable(gasStation),
                        Optional.ofNullable(foodPlaces),
                        Optional.of(photos),
                        Optional.of(transportTypes)
                );

                if (request == null) {
                    log.error("Failed to convert place {} to response object", place.getId());
                    throw new IllegalStateException("Failed to generate response for place");
                }
                requests.add(request);
            }
            return requests;
        } catch (Exception e) {
            throw new PlaceApiException("Problem during getting all places", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PlaceRequest getPlaceById(UUID placeId) {
        log.info("Fetching place with ID: {}", placeId);

        PlaceEntity placeEntity = placeRepository.findById(placeId)
                .orElseThrow(() -> {
                    log.error("Place not found with ID: {}", placeId);
                    return new PlaceNotFoundException("Place not found with ID: " + placeId);
                });

        PlaceType placeType = safeFindAndUnwrap(placeTypeRepository, placeEntity.getPlaceTypeId(), "PlaceType");
        ClimateZone climateZone = safeFindAndUnwrap(climateZoneRepository, placeEntity.getClimateZoneId(), "ClimateZone");
        CountryEntity country = safeFindAndUnwrap(countryRepository, placeEntity.getCountryId(), "Country");
        Photo360 photo360 = safeFindAndUnwrap(photo360Repository, placeEntity.getPhoto360Id(), "Photo360");
        RoadQuality roadQuality = safeFindAndUnwrap(roadQualityRepository, placeEntity.getRoadQualityId(), "RoadQuality");
        NearbyHotels nearbyHotels = safeFindAndUnwrap(nearbyHotelsRepository, placeEntity.getNearbyHotelsId(), "NearbyHotels");
        NearbyHospitals nearbyHospitals = safeFindAndUnwrap(nearbyHospitalsRepository, placeEntity.getNearbyHospitalsId(), "NearbyHospitals");
        NearbyPharmacy nearbyPharmacy = safeFindAndUnwrap(nearbyPharmaciesRepository, placeEntity.getNearbyPharmaciesId(), "NearbyPharmacy");
        GasStation gasStation = safeFindAndUnwrap(gasStationsRepository, placeEntity.getGasStationsId(), "GasStation");
        FoodPlaces foodPlaces = safeFindAndUnwrap(foodPlacesRepository, placeEntity.getFoodPlacesId(), "FoodPlaces");

        List<Photo> photos = safeFindAndConvertToList(photoRepository, placeEntity.getPhotosId(), "Photo");
        List<TransportType> transportTypes = safeFindAndConvertToList(transportTypeRepository, placeEntity.getTransportTypeId(), "TransportType");

        PlaceRequest request = placeEntity.toPlaceRequest(
                Optional.ofNullable(placeType),
                Optional.ofNullable(climateZone),
                Optional.ofNullable(country),
                Optional.ofNullable(photo360),
                Optional.ofNullable(roadQuality),
                Optional.ofNullable(nearbyHotels),
                Optional.ofNullable(nearbyHospitals),
                Optional.ofNullable(nearbyPharmacy),
                Optional.ofNullable(gasStation),
                Optional.ofNullable(foodPlaces),
                Optional.of(photos),
                Optional.of(transportTypes)
        );

        if (request == null) {
            log.error("Failed to convert place {} to response object", placeId);
            throw new IllegalStateException("Failed to generate response for place");
        }

        log.debug("Successfully built response for place {}", placeId);
        return request;
    }

    /**
     * Safely fetches an entity and returns the unwrapped object
     */
    private <T> T safeFindAndUnwrap(JpaRepository<T, UUID> repository, UUID id, String entityName) {
        if (id == null) {
            log.debug("{} ID is null - skipping lookup", entityName);
            return null;
        }

        try {
            return repository.findById(id)
                    .orElseGet(() -> {
                        log.warn("{} with ID {} not found in database", entityName, id);
                        return null;
                    });
        } catch (Exception e) {
            log.error("Error fetching {} with ID {}: {}", entityName, id, e.getMessage());
            return null;
        }
    }

    /**
     * Safely fetches an entity and converts it to a list
     */
    private <T> List<T> safeFindAndConvertToList(JpaRepository<T, UUID> repository, UUID id, String entityName) {
        if (id == null) {
            log.debug("{} ID is null - returning empty list", entityName);
            return Collections.emptyList();
        }

        try {
            return repository.findById(id)
                    .map(Collections::singletonList)
                    .orElseGet(() -> {
                        log.warn("{} with ID {} not found - returning empty list", entityName, id);
                        return Collections.emptyList();
                    });
        } catch (Exception e) {
            log.error("Error fetching {} with ID {}: {}", entityName, id, e.getMessage());
            return Collections.emptyList();
        }
    }

    private <T> Optional<T> safeFind(JpaRepository<T, UUID> repository, UUID id) {
        if (id == null) {
            return Optional.empty();
        }
        try {
            return repository.findById(id);
        } catch (Exception e) {
            log.error("Error fetching entity with ID {} from repository {}", id, repository.getClass().getSimpleName(), e);
            return Optional.empty();
        }
    }

    @Override
    public List<PlaceRequest> getRecommendedPlaces() {
        List<PlaceRequest> requests = new ArrayList<>();
        try {
            List<PlaceEntity> list = placeRepository.findAllByRecommendedTrue()
                    .stream()
                    .toList();

            for (PlaceEntity place : list) {
                PlaceType placeType = safeFindAndUnwrap(placeTypeRepository, place.getPlaceTypeId(), "PlaceType");
                ClimateZone climateZone = safeFindAndUnwrap(climateZoneRepository, place.getClimateZoneId(), "ClimateZone");
                CountryEntity country = safeFindAndUnwrap(countryRepository, place.getCountryId(), "Country");
                Photo360 photo360 = safeFindAndUnwrap(photo360Repository, place.getPhoto360Id(), "Photo360");
                RoadQuality roadQuality = safeFindAndUnwrap(roadQualityRepository, place.getRoadQualityId(), "RoadQuality");
                NearbyHotels nearbyHotels = safeFindAndUnwrap(nearbyHotelsRepository, place.getNearbyHotelsId(), "NearbyHotels");
                NearbyHospitals nearbyHospitals = safeFindAndUnwrap(nearbyHospitalsRepository, place.getNearbyHospitalsId(), "NearbyHospitals");
                NearbyPharmacy nearbyPharmacy = safeFindAndUnwrap(nearbyPharmaciesRepository, place.getNearbyPharmaciesId(), "NearbyPharmacy");
                GasStation gasStation = safeFindAndUnwrap(gasStationsRepository, place.getGasStationsId(), "GasStation");
                FoodPlaces foodPlaces = safeFindAndUnwrap(foodPlacesRepository, place.getFoodPlacesId(), "FoodPlaces");

                List<Photo> photos = safeFindAndConvertToList(photoRepository, place.getPhotosId(), "Photo");
                List<TransportType> transportTypes = safeFindAndConvertToList(transportTypeRepository, place.getTransportTypeId(), "TransportType");

                // Debug print or log field null checks
                log.info("Place ID {} - placeType: {}, climateZone: {}, country: {}, photo360: {}, roadQuality: {}, nearbyHotels: {}, nearbyHospitals: {}, nearbyPharmacy: {}, gasStation: {}, foodPlaces: {}, photos: {}, transportTypes: {}, keywords: {}",
                        place.getId(),
                        placeType == null ? "null" : "OK",
                        climateZone == null ? "null" : "OK",
                        country == null ? "null" : "OK",
                        photo360 == null ? "null" : "OK",
                        roadQuality == null ? "null" : "OK",
                        nearbyHotels == null ? "null" : "OK",
                        nearbyHospitals == null ? "null" : "OK",
                        nearbyPharmacy == null ? "null" : "OK",
                        gasStation == null ? "null" : "OK",
                        foodPlaces == null ? "null" : "OK",
                        (photos == null || photos.isEmpty()) ? "null" : "OK",
                        (transportTypes == null || transportTypes.isEmpty()) ? "null" : "OK"
                );

                PlaceRequest request = place.toPlaceRequest(
                        Optional.ofNullable(placeType),
                        Optional.ofNullable(climateZone),
                        Optional.ofNullable(country),
                        Optional.ofNullable(photo360),
                        Optional.ofNullable(roadQuality),
                        Optional.ofNullable(nearbyHotels),
                        Optional.ofNullable(nearbyHospitals),
                        Optional.ofNullable(nearbyPharmacy),
                        Optional.ofNullable(gasStation),
                        Optional.ofNullable(foodPlaces),
                        Optional.of(photos),
                        Optional.of(transportTypes)
                );

                if (request == null) {
                    log.error("Failed to convert place {} to response object", place.getId());
                    throw new IllegalStateException("Failed to generate response for place");
                }
                requests.add(request);
            }
            return requests;
        } catch (Exception e) {
            throw new PlaceApiException("Problem during getting all places", e);
        }
    }


    @Override
    public List<PlaceRequest> searchPlaces(
            UUID placeTypeId,
            UUID countryId,
            UUID roadQualityId,
            UUID nearbyHotelsId,
            UUID nearbyHospitalsId,
            UUID nearbyPharmaciesId,
            UUID gasStationsId,
            UUID foodPlacesId,
            UUID climateZoneId
    ) {
        Specification<PlaceEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (placeTypeId != null) {
                predicates.add(cb.equal(root.get("placeTypeId"), placeTypeId));
            }
            if (countryId != null) {
                predicates.add(cb.equal(root.get("countryId"), countryId));
            }
            if (roadQualityId != null) {
                predicates.add(cb.equal(root.get("roadQualityId"), roadQualityId));
            }
            if (nearbyHotelsId != null) {
                predicates.add(cb.equal(root.get("nearbyHotelsId"), nearbyHotelsId));
            }
            if (nearbyHospitalsId != null) {
                predicates.add(cb.equal(root.get("nearbyHospitalsId"), nearbyHospitalsId));
            }
            if (nearbyPharmaciesId != null) {
                predicates.add(cb.equal(root.get("nearbyPharmaciesId"), nearbyPharmaciesId));
            }
            if (gasStationsId != null) {
                predicates.add(cb.equal(root.get("gasStationsId"), gasStationsId));
            }
            if (foodPlacesId != null) {
                predicates.add(cb.equal(root.get("foodPlacesId"), foodPlacesId));
            }
            if (climateZoneId != null) {
                predicates.add(cb.equal(root.get("climateZoneId"), climateZoneId));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        List<PlaceEntity> places = placeRepository.findAll(spec);
        List<PlaceRequest> requests = new ArrayList<>();

        for (PlaceEntity place : places) {
            PlaceType placeType = safeFindAndUnwrap(placeTypeRepository, place.getPlaceTypeId(), "PlaceType");
            ClimateZone climateZone = safeFindAndUnwrap(climateZoneRepository, place.getClimateZoneId(), "ClimateZone");
            CountryEntity country = safeFindAndUnwrap(countryRepository, place.getCountryId(), "Country");
            Photo360 photo360 = safeFindAndUnwrap(photo360Repository, place.getPhoto360Id(), "Photo360");
            RoadQuality roadQuality = safeFindAndUnwrap(roadQualityRepository, place.getRoadQualityId(), "RoadQuality");
            NearbyHotels nearbyHotels = safeFindAndUnwrap(nearbyHotelsRepository, place.getNearbyHotelsId(), "NearbyHotels");
            NearbyHospitals nearbyHospitals = safeFindAndUnwrap(nearbyHospitalsRepository, place.getNearbyHospitalsId(), "NearbyHospitals");
            NearbyPharmacy nearbyPharmacy = safeFindAndUnwrap(nearbyPharmaciesRepository, place.getNearbyPharmaciesId(), "NearbyPharmacy");
            GasStation gasStation = safeFindAndUnwrap(gasStationsRepository, place.getGasStationsId(), "GasStation");
            FoodPlaces foodPlaces = safeFindAndUnwrap(foodPlacesRepository, place.getFoodPlacesId(), "FoodPlaces");

            List<Photo> photos = safeFindAndConvertToList(photoRepository, place.getPhotosId(), "Photo");
            List<TransportType> transportTypes = safeFindAndConvertToList(transportTypeRepository, place.getTransportTypeId(), "TransportType");

            log.info("Search Place ID {} - placeType: {}, climateZone: {}, country: {}, photo360: {}, roadQuality: {}, nearbyHotels: {}, nearbyHospitals: {}, nearbyPharmacy: {}, gasStation: {}, foodPlaces: {}, photos: {}, transportTypes: {}, keywords: {}",
                    place.getId(),
                    placeType == null ? "null" : "OK",
                    climateZone == null ? "null" : "OK",
                    country == null ? "null" : "OK",
                    photo360 == null ? "null" : "OK",
                    roadQuality == null ? "null" : "OK",
                    nearbyHotels == null ? "null" : "OK",
                    nearbyHospitals == null ? "null" : "OK",
                    nearbyPharmacy == null ? "null" : "OK",
                    gasStation == null ? "null" : "OK",
                    foodPlaces == null ? "null" : "OK",
                    (photos == null || photos.isEmpty()) ? "null" : "OK",
                    (transportTypes == null || transportTypes.isEmpty()) ? "null" : "OK"
            );

            PlaceRequest request = place.toPlaceRequest(
                    Optional.ofNullable(placeType),
                    Optional.ofNullable(climateZone),
                    Optional.ofNullable(country),
                    Optional.ofNullable(photo360),
                    Optional.ofNullable(roadQuality),
                    Optional.ofNullable(nearbyHotels),
                    Optional.ofNullable(nearbyHospitals),
                    Optional.ofNullable(nearbyPharmacy),
                    Optional.ofNullable(gasStation),
                    Optional.ofNullable(foodPlaces),
                    Optional.of(photos),
                    Optional.of(transportTypes)
            );

            if (request == null) {
                log.error("Failed to convert place {} to response object", place.getId());
                throw new IllegalStateException("Failed to generate response for place");
            }
            requests.add(request);
        }

        return requests;
    }



    @Override
    public List<PlaceRequest> searchPlacesByKeywords(List<String> keywordValues) {
        List<PlaceEntity> allPlaces = placeRepository.findAll();
        List<PlaceRequest> result = new ArrayList<>();

        for (PlaceEntity place : allPlaces) {
            List<Keywords> keywords = safeFindAndConvertToList(keywordRepository, place.getKeywordId(), "Keyword");

            Set<String> placeKeywordSet = keywords.stream()
                    .map(Keywords::getName)
                    .collect(Collectors.toSet());

            boolean allMatch = keywordValues.stream().allMatch(placeKeywordSet::contains);

            if (allMatch) {
                PlaceType placeType = safeFindAndUnwrap(placeTypeRepository, place.getPlaceTypeId(), "PlaceType");
                ClimateZone climateZone = safeFindAndUnwrap(climateZoneRepository, place.getClimateZoneId(), "ClimateZone");
                CountryEntity country = safeFindAndUnwrap(countryRepository, place.getCountryId(), "Country");
                Photo360 photo360 = safeFindAndUnwrap(photo360Repository, place.getPhoto360Id(), "Photo360");
                RoadQuality roadQuality = safeFindAndUnwrap(roadQualityRepository, place.getRoadQualityId(), "RoadQuality");
                NearbyHotels nearbyHotels = safeFindAndUnwrap(nearbyHotelsRepository, place.getNearbyHotelsId(), "NearbyHotels");
                NearbyHospitals nearbyHospitals = safeFindAndUnwrap(nearbyHospitalsRepository, place.getNearbyHospitalsId(), "NearbyHospitals");
                NearbyPharmacy nearbyPharmacy = safeFindAndUnwrap(nearbyPharmaciesRepository, place.getNearbyPharmaciesId(), "NearbyPharmacy");
                GasStation gasStation = safeFindAndUnwrap(gasStationsRepository, place.getGasStationsId(), "GasStation");
                FoodPlaces foodPlaces = safeFindAndUnwrap(foodPlacesRepository, place.getFoodPlacesId(), "FoodPlaces");
                List<Photo> photos = safeFindAndConvertToList(photoRepository, place.getPhotosId(), "Photo");
                List<TransportType> transportTypes = safeFindAndConvertToList(transportTypeRepository, place.getTransportTypeId(), "TransportType");

                PlaceRequest request = place.toPlaceRequest(
                        Optional.ofNullable(placeType),
                        Optional.ofNullable(climateZone),
                        Optional.ofNullable(country),
                        Optional.ofNullable(photo360),
                        Optional.ofNullable(roadQuality),
                        Optional.ofNullable(nearbyHotels),
                        Optional.ofNullable(nearbyHospitals),
                        Optional.ofNullable(nearbyPharmacy),
                        Optional.ofNullable(gasStation),
                        Optional.ofNullable(foodPlaces),
                        Optional.of(photos),
                        Optional.of(transportTypes)
                );

                result.add(request);
            }
        }

        return result;
    }




//    @Override
//    public PlaceRequest getPlacesByKeyWords(List<String> keywords) {
//        PlaceEntity placeEntity = placeRepository.findByKeywords(keywords)
//                .orElseThrow(() -> new PlaceNotFoundException("Place not found with given boolean"));
//        PlaceRequest placeRequest = placeEntity.toPlaceRequest();
//        return placeRequest;
//    }

}
