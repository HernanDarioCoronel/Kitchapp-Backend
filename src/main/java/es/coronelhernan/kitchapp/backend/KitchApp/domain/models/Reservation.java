package es.coronelhernan.kitchapp.backend.KitchApp.domain.models;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.enums.ReservationStatus;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Reservation {
    @EqualsAndHashCode.Include
    private UUID id;
    private String customerName;
    private String customerPhone;
    private Short numGuests;
    private OffsetDateTime reservationDate;
    private RestaurantTable restaurantTables;
    private ReservationStatus status;
    private String notes;
}