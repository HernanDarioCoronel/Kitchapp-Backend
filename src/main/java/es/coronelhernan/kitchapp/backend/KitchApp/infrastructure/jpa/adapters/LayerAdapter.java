package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.adapters;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Layer;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.LayerRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories.LayerEntityRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping.LayerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LayerAdapter implements LayerRepository {
    private final LayerEntityRepository jpaRepository;
    private final LayerMapper mapper;

    @Override
    public Layer save(Layer domain) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(domain)));
    }

    @Override
    public Optional<Layer> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Layer> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void delete(Layer domain) {
        jpaRepository.deleteById(domain.getId());
    }
}
