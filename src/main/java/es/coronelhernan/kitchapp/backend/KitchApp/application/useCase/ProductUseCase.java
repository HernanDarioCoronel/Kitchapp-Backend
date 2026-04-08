package es.coronelhernan.kitchapp.backend.KitchApp.application.useCase;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Product;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public void delete(UUID id) {
        var product = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        this.repository.delete(product);
    }
}

