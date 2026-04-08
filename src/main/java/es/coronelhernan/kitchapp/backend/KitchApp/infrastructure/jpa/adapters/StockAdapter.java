package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.adapters;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Stock;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.StockRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories.StockEntityRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping.StockMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StockAdapter implements StockRepository {
    private final StockEntityRepository jpaRepository;
    private final StockMapper mapper;

    @Override
    public Stock save(Stock domain) {
        var savedEntity = this.jpaRepository.save(this.mapper.toEntity(domain));
        return this.mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Stock> findById(UUID id) {
        return this.jpaRepository.findById(id)
                .map(this.mapper::toDomain);
    }

    @Override
    public List<Stock> findAll() {
        return this.jpaRepository.findAll().stream()
                .map(this.mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(Stock domain) {
        this.jpaRepository.deleteById(domain.getId());
    }
}

