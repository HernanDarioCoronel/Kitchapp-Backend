package es.coronelhernan.kitchapp.backend.KitchApp.domain.models;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.exceptions.TableUnavailableException;
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

    public TableOccupation occupy() {
        if (!this.isActive) {
            throw new TableUnavailableException("La mesa no esta disponible para ocupar");
        }

        return TableOccupation.start(this);
    }
}