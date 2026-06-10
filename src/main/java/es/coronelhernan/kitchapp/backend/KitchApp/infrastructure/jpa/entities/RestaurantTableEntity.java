package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "restaurant_tables", schema = "public", uniqueConstraints = {@UniqueConstraint(name = "restaurant_tables_table_number_key",
        columnNames = {"table_number"})})
public class RestaurantTableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "table_number", nullable = false)
    private Short tableNumber;

    @Column(name = "capacity")
    private Short capacity;

    @ColumnDefault("true")
    @Column(name = "is_active")
    private Boolean isActive;

    @ColumnDefault("0")
    @Column(name = "x", precision = 10, scale = 2)
    private java.math.BigDecimal x;

    @ColumnDefault("0")
    @Column(name = "y", precision = 10, scale = 2)
    private java.math.BigDecimal y;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "layer_id")
    private LayerEntity layer;

}