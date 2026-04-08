package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.adapters;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Supplier;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.SupplierRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories.SupplierEntityRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping.SupplierMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SupplierAdapter implements SupplierRepository {
    private final SupplierEntityRepository jpaRepository;
    private final SupplierMapper mapper;

    @Override
    public Supplier save(Supplier domain) {
        var savedEntity = this.jpaRepository.save(this.mapper.toEntity(domain));
        return this.mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Supplier> findById(UUID id) {
        return this.jpaRepository.findById(id)
                .map(this.mapper::toDomain);
    }

    @Override
    public List<Supplier> findAll() {
        return this.jpaRepository.findAll().stream()
                .map(this.mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(Supplier domain) {
        this.jpaRepository.deleteById(domain.getId());
    }
}

