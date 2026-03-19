package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
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

}