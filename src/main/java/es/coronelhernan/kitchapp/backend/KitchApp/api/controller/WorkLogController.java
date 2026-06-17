package es.coronelhernan.kitchapp.backend.KitchApp.api.controller;

import es.coronelhernan.kitchapp.backend.KitchApp.api.dto.worklog.WorkLogRequest;
import es.coronelhernan.kitchapp.backend.KitchApp.api.dto.worklog.WorkLogResponse;
import es.coronelhernan.kitchapp.backend.KitchApp.api.mapper.WorkLogApiMapper;
import es.coronelhernan.kitchapp.backend.KitchApp.application.useCase.WorkLogUseCase;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.enums.WorkLogType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/work-logs")
@RequiredArgsConstructor
@Tag(name = "Work Logs - Fichaje")
public class WorkLogController {

    private final WorkLogUseCase useCase;
    private final WorkLogApiMapper mapper;

    @PostMapping
    @Operation(summary = "Register a work log event (fichaje)")
    public ResponseEntity<WorkLogResponse> create(@Valid @RequestBody WorkLogRequest request) {
        var workLog = useCase.create(mapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(workLog));
    }

    @GetMapping
    @Operation(summary = "List work log events with optional filters")
    public ResponseEntity<List<WorkLogResponse>> findAll(
            @RequestParam(required = false) UUID employeeId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime to,
            @RequestParam(required = false) WorkLogType type
    ) {
        var results = useCase.findFiltered(employeeId, from, to, type).stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(results);
    }
}
