package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.adapters;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Allergen;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.AllergenRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories.AllergenEntityRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping.AllergenMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AllergenAdapter implements AllergenRepository {
    private final AllergenEntityRepository jpaEntity;
    private final AllergenMapper mapper;

    @Override
    public Allergen save(Allergen entity) {
        var savedEntity = this.jpaEntity.save(this.mapper.toEntity(entity));
        return this.mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Allergen> findById(UUID id) {
        return this.jpaEntity.findById(id)
                .map(this.mapper::toDomain);
    }

    @Override
    public List<Allergen> findAll() {
        return this.jpaEntity.findAll().stream()
                .map(this.mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(Allergen entity) {
        this.jpaEntity.deleteById(entity.getId());
    }
}
