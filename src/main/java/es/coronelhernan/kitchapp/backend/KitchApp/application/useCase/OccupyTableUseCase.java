package es.coronelhernan.kitchapp.backend.KitchApp.application.useCase;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.exceptions.TableUnavailableException;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.TableOccupation;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.RestaurantTableRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.TableOccupationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OccupyTableUseCase {
    private final RestaurantTableRepository tableRepository;
    private final TableOccupationRepository occupationRepository;

    @Transactional
    public TableOccupation execute(UUID tableId){
        var table = this.tableRepository.findById(tableId)
                .orElseThrow(() -> new IllegalArgumentException("Mesa no encontrada"));

        if (this.occupationRepository.findOpenByTableId(tableId).isPresent()) {
            throw new IllegalStateException("La mesa ya tiene una ocupacion activa");
        }

        try {
            var occupation = table.occupy();
            return this.occupationRepository.save(occupation.toBuilder().id(null).build());
        } catch (TableUnavailableException ex) {
            throw new IllegalStateException("No se puede ocupar la mesa", ex);
        }
    }
}
