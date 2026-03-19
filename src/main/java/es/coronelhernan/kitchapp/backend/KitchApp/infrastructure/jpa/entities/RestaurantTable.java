package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

@Entity
@Table(name = "restaurant_tables", schema = "public", uniqueConstraints = {@UniqueConstraint(name = "restaurant_tables_table_number_key",
        columnNames = {"table_number"})})
public class RestaurantTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "table_number", nullable = false)
    private Short tableNumber;

    @Column(name = "capacity")
    private Short capacity;

    @ColumnDefault("true")
    @Column(name = "is_active")
    private Boolean isActive;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Short getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Short tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Short getCapacity() {
        return capacity;
    }

    public void setCapacity(Short capacity) {
        this.capacity = capacity;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

}