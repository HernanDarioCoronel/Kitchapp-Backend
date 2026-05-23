package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.adapters;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.enums.ProductType;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Product;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.ProductRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories.ProductEntityRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductAdapter implements ProductRepository {
    private final ProductEntityRepository jpaRepository;
    private final ProductMapper mapper;

    @Override
    public Product save(Product domain) {
        var savedEntity = this.jpaRepository.save(this.mapper.toEntity(domain));
        return this.mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return this.jpaRepository.findById(id)
                .map(this.mapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return this.jpaRepository.findAll().stream()
                .map(this.mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(Product domain) {
        this.jpaRepository.deleteById(domain.getId());
    }

    @Override
    public List<Product> findAllByType(ProductType type) {
        return this.jpaRepository.findByType(type).stream()
                .map(this.mapper::toDomain)
                .toList();
    }
}

