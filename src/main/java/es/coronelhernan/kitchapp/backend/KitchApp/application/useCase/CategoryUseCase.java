package es.coronelhernan.kitchapp.backend.KitchApp.application.useCase;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Category;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import es.coronelhernan.kitchapp.backend.KitchApp.application.utils.PatchUtils;

@RequiredArgsConstructor
@Service
public class CategoryUseCase {
    private final CategoryRepository repository;

    @Transactional
    public Category save(Category category) {
        return this.repository.save(category);
    }

    @Transactional
    public Optional<Category> findById(UUID id) {
        return this.repository.findById(id);
    }

    @Transactional
    public List<Category> findAll() {
        return this.repository.findAll();
    }

    @Transactional
    public void delete(UUID id) {
        var category = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria no encontrada"));
        this.repository.delete(category);
    }

    @Transactional
    public Category update(UUID id, Category patch) {
        var category = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria no encontrada"));
        PatchUtils.copyNonNullProperties(patch, category, "id", "createdAt");
        return this.repository.save(category);
    }
}

