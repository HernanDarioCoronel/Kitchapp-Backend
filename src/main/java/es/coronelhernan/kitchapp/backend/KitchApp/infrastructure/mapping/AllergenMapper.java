package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Allergen;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.AllergenEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AllergenMapper {
    @Mapping(target = "product", ignore = true)
    Allergen toDomain(AllergenEntity entity);

    AllergenEntity toEntity(Allergen domain);
}

