package es.coronelhernan.kitchapp.backend.KitchApp.domain.models;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PurchaseOrderLine {
    @EqualsAndHashCode.Include
    private UUID id;
    private PurchaseOrder order;
    private Product product;
    private String description;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private Tax tax;
    private BigDecimal lineSubtotal;
}