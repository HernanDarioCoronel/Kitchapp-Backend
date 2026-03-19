package es.coronelhernan.kitchapp.backend.KitchApp.domain.models;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.enums.PurchaseStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PurchaseOrder {
    @EqualsAndHashCode.Include
    private UUID id;
    private Supplier supplier;
    private String orderNumber;
    private PurchaseStatus status;
    private OffsetDateTime createdAt;
    private LocalDate dueDate;
    private OffsetDateTime updatedAt;
    private BigDecimal netAmount;
    private BigDecimal taxAmount;
    private BigDecimal total;
    private String notes;
    private List<PurchaseOrderLine> purchaseOrderLines;
}