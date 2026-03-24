package es.coronelhernan.kitchapp.backend.KitchApp.application.useCase;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.RestaurantTableRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.TableOccupationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OccupyTableUseCase {
    private final RestaurantTableRepository tableRepository;
    private final TableOccupationRepository occupationRepository;

    public void execute(UUID tableId){

    }
}
