package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.InventoryMovement;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.InventoryMovementEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, EmployeeMapper.class})
public interface InventoryMovementMapper {
    InventoryMovement toDomain(InventoryMovementEntity entity);

    InventoryMovementEntity toEntity(InventoryMovement domain);
}

