package es.coronelhernan.kitchapp.backend.KitchApp.domain.models;

import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Stock {
    @EqualsAndHashCode.Include
    private UUID id;
    private Product product;
    private BigDecimal currentQty;
    private BigDecimal minStock;
    private OffsetDateTime updatedAt;
}