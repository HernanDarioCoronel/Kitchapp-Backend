package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.TableOccupation;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.TableOccupationEntity;
import org.springframework.stereotype.Component;

@Component
public class TableOccupationMapper {
    public TableOccupation toDomain(TableOccupation entity) {
        return TableOccupation.builder()
                .id(entity.getId())
                .table(entity.getTable())
                .startedAt(entity.getStartedAt())
                .endedAt(entity.getEndedAt())
                .status(entity.getStatus())
                .build();
    }

    public TableOccupationEntity toEntity(TableOccupation domain) {
        return TableOccupationEntity.builder()
                .id(domain.getId())
                //.table(domain.getTable()) TO DO: mapper de table
                .startedAt(domain.getStartedAt())
                .endedAt(domain.getEndedAt())
                .status(domain.getStatus())
                .build();
    }
}
