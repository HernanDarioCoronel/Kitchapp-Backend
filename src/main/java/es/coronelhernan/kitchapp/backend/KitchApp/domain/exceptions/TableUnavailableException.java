package es.coronelhernan.kitchapp.backend.KitchApp.domain.exceptions;

public class TableUnavailableException extends RuntimeException {
    public TableUnavailableException(String message) {
        super(message);
    }
}

