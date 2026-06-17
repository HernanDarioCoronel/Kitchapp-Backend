package es.coronelhernan.kitchapp.backend.KitchApp.api.dto.worklog;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.enums.WorkLogType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;
import java.util.UUID;

public record WorkLogRequest(
        @NotNull UUID id,
        @NotNull UUID employeeId,
        @NotNull WorkLogType type,
        @NotNull OffsetDateTime timestamp,
        @Size(max = 500) String notes
) {}
