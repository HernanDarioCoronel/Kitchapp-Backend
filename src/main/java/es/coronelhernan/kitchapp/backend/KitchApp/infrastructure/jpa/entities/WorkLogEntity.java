package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.enums.WorkLogType;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "work_logs", schema = "public")
public class WorkLogEntity {

    @Id
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Include
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeEntity employee;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private WorkLogType type;

    @Column(name = "timestamp", nullable = false)
    private OffsetDateTime timestamp;

    @Column(name = "notes", length = 500)
    private String notes;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
}
