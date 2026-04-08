package es.coronelhernan.kitchapp.backend.KitchApp.application.useCase.auth.model;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.enums.EmployeeRole;

import java.util.UUID;

public record AuthUserSnapshot(
        UUID id,
        String username,
        String passwordHash,
        boolean employeeActive,
        EmployeeRole role
) {
}

