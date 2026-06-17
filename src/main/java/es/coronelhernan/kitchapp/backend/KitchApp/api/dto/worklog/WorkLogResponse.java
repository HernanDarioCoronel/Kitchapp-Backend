package es.coronelhernan.kitchapp.backend.KitchApp.api.dto.worklog;

import com.fasterxml.jackson.annotation.JsonFormat;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.enums.WorkLogType;

import java.time.OffsetDateTime;
import java.util.UUID;

public record WorkLogResponse(
        UUID id,
        EmployeeInfo employee,
        WorkLogType type,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX") OffsetDateTime timestamp,
        String notes,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX") OffsetDateTime createdAt
) {
    public record EmployeeInfo(
            UUID id,
            String fullName,
            String role,
            Boolean isActive
    ) {}
}
