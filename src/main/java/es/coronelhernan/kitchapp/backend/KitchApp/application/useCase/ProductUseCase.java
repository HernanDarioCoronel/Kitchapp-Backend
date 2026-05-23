package es.coronelhernan.kitchapp.backend.KitchApp.application.useCase;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.enums.ProductType;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Product;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import es.coronelhernan.kitchapp.backend.KitchApp.application.utils.PatchUtils;

@RequiredArgsConstructor
@Service
public class ProductUseCase {
    private final ProductRepository repository;

    @Transactional
    public Product save(Product product) {
        return this.repository.save(product);
    }

    @Transactional
    public Optional<Product> findById(UUID id) {
        return this.repository.findById(id);
    }

    @Transactional
    public List<Product> findAll() {
        return this.repository.findAll();
    }

    @Transactional
    public List<Product> findAllByType(int type) {
        return switch (type) {
            case 0 -> this.repository.findAll();
            case 1 -> this.repository.findAllByType(ProductType.INGREDIENT);
            case 2 -> this.repository.findAllByType(ProductType.PRODUCT);
            default -> throw new IllegalArgumentException("Tipo de producto no valido: " + type);
        };
    }

    @Transactional
    public void delete(UUID id) {
        var product = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        this.repository.delete(product);
    }

    @Transactional
    public Product update(UUID id, Product patch) {
        var product = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        PatchUtils.copyNonNullProperties(patch, product, "id", "createdAt");
        return this.repository.save(product);
    }
}

