package es.coronelhernan.kitchapp.backend.KitchApp.domain.models;

import lombok.*;

import java.util.UUID;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RestaurantTable {
    @EqualsAndHashCode.Include
    private UUID id;
    private Short tableNumber;
    private Short capacity;
    private Boolean isActive;
}