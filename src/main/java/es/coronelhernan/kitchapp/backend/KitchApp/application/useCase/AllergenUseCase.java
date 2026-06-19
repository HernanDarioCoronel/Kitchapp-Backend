package es.coronelhernan.kitchapp.backend.KitchApp.application.useCase;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Allergen;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.AllergenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import es.coronelhernan.kitchapp.backend.KitchApp.application.utils.PatchUtils;

@RequiredArgsConstructor
@Service
public class AllergenUseCase {
    private final AllergenRepository allergenRepository;

    @Transactional
    public Allergen create(String name, String description) {
        var allergen = Allergen.builder()
                .name(name)
                .description(description)
                .build();

        return this.allergenRepository.save(allergen);
    }

    @Transactional
    public Allergen save(Allergen allergen) {
        return this.allergenRepository.save(allergen.toBuilder().id(null).build());
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

    @Transactional
    public Allergen update(UUID id, Allergen patch) {
        var allergen = this.allergenRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Alergeno no encontrado"));
        PatchUtils.copyNonNullProperties(patch, allergen, "id", "createdAt");
        return this.allergenRepository.save(allergen);
    }
}
