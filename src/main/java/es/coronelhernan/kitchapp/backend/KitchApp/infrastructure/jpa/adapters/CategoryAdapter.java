package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.adapters;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Category;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.CategoryRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories.CategoryEntityRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CategoryAdapter implements CategoryRepository {
    private final CategoryEntityRepository jpaRepository;
    private final CategoryMapper mapper;

    @Override
    public Category save(Category domain) {
        var savedEntity = this.jpaRepository.save(this.mapper.toEntity(domain));
        return this.mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Category> findById(UUID id) {
        return this.jpaRepository.findById(id)
                .map(this.mapper::toDomain);
    }

    @Override
    public List<Category> findAll() {
        return this.jpaRepository.findAll().stream()
                .map(this.mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(Category domain) {
        this.jpaRepository.deleteById(domain.getId());
    }
}

