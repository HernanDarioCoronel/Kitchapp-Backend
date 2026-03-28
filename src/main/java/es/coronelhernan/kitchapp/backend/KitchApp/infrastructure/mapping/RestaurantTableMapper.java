package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.RestaurantTable;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.RestaurantTableEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestaurantTableMapper {
    RestaurantTable toDomain(RestaurantTableEntity entity);
    RestaurantTableEntity toEntity(RestaurantTable domain);
}
