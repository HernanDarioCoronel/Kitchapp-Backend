package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Tax;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.TaxEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaxMapper {
    Tax toDomain(TaxEntity entity);

    TaxEntity toEntity(Tax domain);
}

