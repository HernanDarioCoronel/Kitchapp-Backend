package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.enums.PurchaseStatus;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "purchase_orders", schema = "public")
public class PurchaseOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Include
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "supplier_id", nullable = false)
    private SupplierEntity supplier;

    @Column(name = "order_number", nullable = false, length = 20)
    private String orderNumber;

    @ColumnDefault("'DRAFT'")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "purchase_status not null")
    private PurchaseStatus status;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "net_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal netAmount;

    @Column(name = "tax_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal taxAmount;

    @Column(name = "total", nullable = false, precision = 12, scale = 2)
    private BigDecimal total;

    @Column(name = "notes", length = Integer.MAX_VALUE)
    private String notes;
}