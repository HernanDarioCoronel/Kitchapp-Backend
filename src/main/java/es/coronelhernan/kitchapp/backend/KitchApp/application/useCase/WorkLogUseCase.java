package es.coronelhernan.kitchapp.backend.KitchApp.application.useCase;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.enums.WorkLogType;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.WorkLog;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.EmployeeRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.WorkLogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class WorkLogUseCase {

    private final WorkLogRepository repository;
    private final EmployeeRepository employeeRepository;

    @Transactional
    public WorkLog create(WorkLog workLog) {
        var employee = employeeRepository.findById(workLog.getEmployee().getId())
                .filter(e -> Boolean.TRUE.equals(e.getIsActive()))
                .orElseThrow(() -> new NoSuchElementException("Employee not found or not active"));

        return repository.save(workLog.toBuilder()
                .employee(employee)
                .createdAt(OffsetDateTime.now())
                .build());
    }

    @Transactional
    public List<WorkLog> findFiltered(UUID employeeId, OffsetDateTime from, OffsetDateTime to, WorkLogType type) {
        return repository.findFiltered(employeeId, from, to, type);
    }
}
