package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.adapters;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Allergen;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.AllergenRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories.AllergenEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AllergenAdapter implements AllergenRepository {
    private final AllergenEntityRepository jpaEntity;

    @Override
    public Allergen save(Allergen entity) {
        return null;
    }

    @Override
    public Optional<Allergen> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Allergen> findAll() {
        return List.of();
    }

    @Override
    public void delete(Allergen entity) {

    }
}
