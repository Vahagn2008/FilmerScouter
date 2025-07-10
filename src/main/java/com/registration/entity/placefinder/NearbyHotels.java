package com.registration.entity.placefinder;

import com.registration.constants.DatabaseConstants;
import com.registration.request.ClimateZoneRequest;
import com.registration.request.NearbyHospitalsRequest;
import com.registration.request.NearbyHotelsRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.postgresql.hostchooser.HostRequirement;

import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = DatabaseConstants.SCHEMA_NAME, name = DatabaseConstants.NEARBY_HOTELS_TABLE_NAME)
public class NearbyHotels {

    @Id
    @Column(name = "nearby_hotels_id")
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;

    private String name;

    private float rate;

    private String website;

    public NearbyHotels(NearbyHospitalsRequest request) {
        id = request.getId();
        name = request.getName();

    }

    public NearbyHotelsRequest toNearbyHotelsRequest() {
        return new NearbyHotelsRequest(id, name);

    }

}
