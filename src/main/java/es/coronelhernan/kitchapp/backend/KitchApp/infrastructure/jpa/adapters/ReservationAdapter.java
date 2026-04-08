package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.adapters;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Reservation;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.ReservationRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories.ReservationEntityRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping.ReservationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReservationAdapter implements ReservationRepository {
    private final ReservationEntityRepository jpaRepository;
    private final ReservationMapper mapper;

    @Override
    public Reservation save(Reservation domain) {
        var savedEntity = this.jpaRepository.save(this.mapper.toEntity(domain));
        return this.mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Reservation> findById(UUID id) {
        return this.jpaRepository.findById(id)
                .map(this.mapper::toDomain);
    }

    @Override
    public List<Reservation> findAll() {
        return this.jpaRepository.findAll().stream()
                .map(this.mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(Reservation domain) {
        this.jpaRepository.deleteById(domain.getId());
    }
}

