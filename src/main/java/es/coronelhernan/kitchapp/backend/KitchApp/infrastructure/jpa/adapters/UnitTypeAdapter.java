package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.adapters;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.UnitType;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.UnitTypeRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories.UnitTypeEntityRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping.UnitTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UnitTypeAdapter implements UnitTypeRepository {
    private final UnitTypeEntityRepository jpaRepository;
    private final UnitTypeMapper mapper;

    @Override
    public UnitType save(UnitType domain) {
        var savedEntity = this.jpaRepository.save(this.mapper.toEntity(domain));
        return this.mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<UnitType> findById(UUID id) {
        return this.jpaRepository.findById(id)
                .map(this.mapper::toDomain);
    }

    @Override
    public List<UnitType> findAll() {
        return this.jpaRepository.findAll().stream()
                .map(this.mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(UnitType domain) {
        this.jpaRepository.deleteById(domain.getId());
    }
}

