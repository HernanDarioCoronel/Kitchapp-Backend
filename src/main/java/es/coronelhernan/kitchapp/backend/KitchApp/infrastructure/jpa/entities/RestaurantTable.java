package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "restaurant_tables", schema = "public", uniqueConstraints = {@UniqueConstraint(name = "restaurant_tables_table_number_key",
        columnNames = {"table_number"})})
public class RestaurantTable {
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

}