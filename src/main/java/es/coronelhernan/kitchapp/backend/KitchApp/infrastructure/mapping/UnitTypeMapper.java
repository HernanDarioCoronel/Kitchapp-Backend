package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.UnitType;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.UnitTypeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UnitTypeMapper {
    UnitType toDomain(UnitTypeEntity entity);

    UnitTypeEntity toEntity(UnitType domain);
}

