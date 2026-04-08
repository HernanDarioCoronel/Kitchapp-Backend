package es.coronelhernan.kitchapp.backend.KitchApp.application.useCase;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Allergen;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.AllergenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AllergenUseCase {
    private final AllergenRepository allergenRepository;

    @Transactional
    public Allergen create(String name, String description) {
        var allergen = Allergen.builder()
                .id(UUID.randomUUID())
                .name(name)
                .description(description)
                .build();

        return this.allergenRepository.save(allergen);
    }

    @Transactional
    public Allergen save(Allergen allergen) {
        return this.allergenRepository.save(allergen);
    }

    @Transactional
    public void delete(UUID id) {
        var allergen = this.allergenRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Alergeno no encontrado"));

        this.allergenRepository.delete(allergen);
    }

    @Transactional
    public Optional<Allergen> findById(UUID id) {
        return this.allergenRepository.findById(id);
    }

    @Transactional
    public List<Allergen> findAll() {
        return this.allergenRepository.findAll();
    }
}
