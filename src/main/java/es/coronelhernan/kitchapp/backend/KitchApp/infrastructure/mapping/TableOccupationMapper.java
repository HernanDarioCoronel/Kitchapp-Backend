package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.TableOccupation;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.TableOccupationEntity;
import org.springframework.stereotype.Component;

@Component
public class TableOccupationMapper {
    public static TableOccupation toDomain(TableOccupationEntity entity) {
        return TableOccupation.builder()
                .id(entity.getId())
                .table(RestaurantTableMapper.toDomain(entity.getTable()))
                .startedAt(entity.getStartedAt())
                .endedAt(entity.getEndedAt())
                .status(entity.getStatus())
                .build();
    }

    public static TableOccupationEntity toEntity(TableOccupation domain) {
        return TableOccupationEntity.builder()
                .id(domain.getId())
                .table(RestaurantTableMapper.toEntity(domain.getTable()))
                .startedAt(domain.getStartedAt())
                .endedAt(domain.getEndedAt())
                .status(domain.getStatus())
                .build();
    }
}
