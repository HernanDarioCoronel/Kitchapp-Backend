package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.enums.OccupationStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE) // solo para builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(toBuilder = true)
@Table(name = "table_occupations", schema = "public")
public class TableOccupationEntity {
    @Id
    @ColumnDefault("gen_random_uuid()")
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "table_id", nullable = false)
    private RestaurantTableEntity table;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "started_at", nullable = false)
    private OffsetDateTime startedAt;

    @Column(name = "ended_at")
    private OffsetDateTime endedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private OccupationStatus status;
}