package es.coronelhernan.kitchapp.backend.KitchApp.application.useCase;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.RestaurantTable;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.RestaurantTableRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import es.coronelhernan.kitchapp.backend.KitchApp.application.utils.PatchUtils;

@RequiredArgsConstructor
@Service
public class RestaurantTableUseCase {
    private final RestaurantTableRepository repository;

    @Transactional
    public RestaurantTable save(RestaurantTable table) {
        return this.repository.save(table.toBuilder().id(null).build());
    }

    @Transactional
    public Optional<RestaurantTable> findById(UUID id) {
        return this.repository.findById(id);
    }

    @Transactional
    public List<RestaurantTable> findAll() {
        return this.repository.findAll();
    }

    @Transactional
    public void delete(UUID id) {
        var table = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Mesa no encontrada"));
        this.repository.delete(table);
    }

    @Transactional
    public RestaurantTable update(UUID id, RestaurantTable patch) {
        var table = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Mesa no encontrada"));
        PatchUtils.copyNonNullProperties(patch, table, "id", "createdAt");
        return this.repository.save(table);
    }
}

