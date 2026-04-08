package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.adapters;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.CashDrawer;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.CashDrawerRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories.CashDrawerEntityRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping.CashDrawerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CashDrawerAdapter implements CashDrawerRepository {
    private final CashDrawerEntityRepository jpaRepository;
    private final CashDrawerMapper mapper;

    @Override
    public CashDrawer save(CashDrawer domain) {
        var savedEntity = this.jpaRepository.save(this.mapper.toEntity(domain));
        return this.mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<CashDrawer> findById(UUID id) {
        return this.jpaRepository.findById(id)
                .map(this.mapper::toDomain);
    }

    @Override
    public List<CashDrawer> findAll() {
        return this.jpaRepository.findAll().stream()
                .map(this.mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(CashDrawer domain) {
        this.jpaRepository.deleteById(domain.getId());
    }
}

