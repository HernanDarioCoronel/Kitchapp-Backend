package es.coronelhernan.kitchapp.backend.KitchApp.application.useCase;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Stock;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.StockRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class StockUseCase {
    private final StockRepository repository;

    @Transactional
    public Stock save(Stock stock) {
        return this.repository.save(stock);
    }

    @Transactional
    public Optional<Stock> findById(UUID id) {
        return this.repository.findById(id);
    }

    @Transactional
    public List<Stock> findAll() {
        return this.repository.findAll();
    }

    @Transactional
    public void delete(UUID id) {
        var stock = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Existencia no encontrada"));
        this.repository.delete(stock);
    }
}

