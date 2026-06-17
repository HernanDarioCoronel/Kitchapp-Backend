package es.coronelhernan.kitchapp.backend.KitchApp.domain.models;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.enums.WorkLogType;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class WorkLog {
    @EqualsAndHashCode.Include
    private UUID id;
    private Employee employee;
    private WorkLogType type;
    private OffsetDateTime timestamp;
    private String notes;
    private OffsetDateTime createdAt;
}
