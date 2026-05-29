package es.coronelhernan.kitchapp.backend.KitchApp.domain.models;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.enums.MovementType;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class InventoryMovement {
    @EqualsAndHashCode.Include
    private UUID id;
    private Product product;
    private Employee employee;
    private BigDecimal quantity;
    private MovementType type;
    private String reason;
    private OffsetDateTime createdAt;
}