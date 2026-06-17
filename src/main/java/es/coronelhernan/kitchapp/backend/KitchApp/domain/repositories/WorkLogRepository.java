package es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.enums.WorkLogType;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.WorkLog;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface WorkLogRepository extends BaseRepository<WorkLog> {
    List<WorkLog> findFiltered(UUID employeeId, OffsetDateTime from, OffsetDateTime to, WorkLogType type);
}
