package es.coronelhernan.kitchapp.backend.KitchApp.domain.models;

import jakarta.servlet.UnavailableException;
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

    public TableOccupation occupy() throws UnavailableException {
        if(!this.isActive){
            throw new UnavailableException("La mesa esta ocupada");
        }

        return TableOccupation.start(this);
    }
}