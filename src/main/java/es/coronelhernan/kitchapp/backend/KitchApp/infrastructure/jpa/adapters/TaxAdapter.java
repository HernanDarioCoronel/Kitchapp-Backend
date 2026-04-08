package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.adapters;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Tax;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.TaxRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories.TaxEntityRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping.TaxMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TaxAdapter implements TaxRepository {
    private final TaxEntityRepository jpaRepository;
    private final TaxMapper mapper;

    @Override
    public Tax save(Tax domain) {
        var savedEntity = this.jpaRepository.save(this.mapper.toEntity(domain));
        return this.mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Tax> findById(UUID id) {
        return this.jpaRepository.findById(id)
                .map(this.mapper::toDomain);
    }

    @Override
    public List<Tax> findAll() {
        return this.jpaRepository.findAll().stream()
                .map(this.mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(Tax domain) {
        this.jpaRepository.deleteById(domain.getId());
    }
}

