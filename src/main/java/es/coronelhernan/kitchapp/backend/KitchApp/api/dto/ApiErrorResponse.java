package es.coronelhernan.kitchapp.backend.KitchApp.api.dto;

import java.util.List;

public record ApiErrorResponse(String message, List<String> details) {
    public ApiErrorResponse(String message) {
        this(message, List.of());
    }
}
