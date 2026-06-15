package es.coronelhernan.kitchapp.backend.KitchApp.application.useCase;

import es.coronelhernan.kitchapp.backend.KitchApp.application.utils.PatchUtils;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Layer;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.LayerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class LayerUseCase {
    private final LayerRepository repository;

    @Transactional
    public Layer save(Layer layer) {
        return repository.save(layer.toBuilder().id(null).build());
    }

    @Transactional
    public Optional<Layer> findById(UUID id) {
        return repository.findById(id);
    }

    @Transactional
    public List<Layer> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void delete(UUID id) {
        var layer = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Layer no encontrado"));
        repository.delete(layer);
    }

    @Transactional
    public Layer update(UUID id, Layer patch) {
        var layer = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Layer no encontrado"));
        PatchUtils.copyNonNullProperties(patch, layer, "id");
        return repository.save(layer);
    }
}
