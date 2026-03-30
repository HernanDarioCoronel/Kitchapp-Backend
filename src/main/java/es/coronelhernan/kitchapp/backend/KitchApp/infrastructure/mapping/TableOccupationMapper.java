package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.TableOccupation;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.TableOccupationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TableOccupationMapper {
    TableOccupation toDomain(TableOccupationEntity entity);
    TableOccupationEntity toEntity(TableOccupation domain);
}
