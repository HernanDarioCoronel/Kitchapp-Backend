package es.coronelhernan.kitchapp.backend.KitchApp.application.useCase;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Reservation;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.ReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ReservationUseCase {
    private final ReservationRepository repository;

    @Transactional
    public Reservation save(Reservation reservation) {
        return this.repository.save(reservation);
    }

    @Transactional
    public Optional<Reservation> findById(UUID id) {
        return this.repository.findById(id);
    }

    @Transactional
    public List<Reservation> findAll() {
        return this.repository.findAll();
    }

    @Transactional
    public void delete(UUID id) {
        var reservation = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));
        this.repository.delete(reservation);
    }
}

