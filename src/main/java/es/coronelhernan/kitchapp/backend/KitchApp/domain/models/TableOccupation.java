package es.coronelhernan.kitchapp.backend.KitchApp.domain.models;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.enums.OccupationStatus;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.exceptions.AlreadyClosedException;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TableOccupation {
    @EqualsAndHashCode.Include
    private UUID id;
    private RestaurantTable table;
    private OffsetDateTime startedAt;
    private OffsetDateTime endedAt;
    private OccupationStatus status;

    public static TableOccupation start(RestaurantTable rt) {
        return TableOccupation.builder()
                .table(rt)
                .startedAt(OffsetDateTime.now())
                .status(OccupationStatus.OPEN)
                .build();
    }

    public TableOccupation close() throws AlreadyClosedException {
        if (this.endedAt != null){
            throw new AlreadyClosedException();
        }
        return this.toBuilder()
                .endedAt(OffsetDateTime.now())
                .status(OccupationStatus.CLOSED)
                .build();
    }

}

