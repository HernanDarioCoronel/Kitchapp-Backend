package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "reservations", schema = "public")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "customer_name", nullable = false, length = 100)
    private String customerName;

    @Column(name = "customer_phone", length = 20)
    private String customerPhone;

    @Column(name = "num_guests", nullable = false)
    private Short numGuests;

    @Column(name = "reservation_date", nullable = false)
    private OffsetDateTime reservationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_tables_id")
    private RestaurantTable restaurantTables;

    @ColumnDefault("'CONFIRMED'")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "reservation_status")
    private ReservationStatus status;

    @Column(name = "notes", length = Integer.MAX_VALUE)
    private String notes;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public Short getNumGuests() {
        return numGuests;
    }

    public void setNumGuests(Short numGuests) {
        this.numGuests = numGuests;
    }

    public OffsetDateTime getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(OffsetDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public RestaurantTable getRestaurantTables() {
        return restaurantTables;
    }

    public void setRestaurantTables(RestaurantTable restaurantTables) {
        this.restaurantTables = restaurantTables;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public enum ReservationStatus {
        CONFIRMED,
        PENDING,
        CANCELLED,
        ARRIVED
    }
}