package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.RestaurantTable;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.RestaurantTableEntity;

public class RestaurantTableMapper {
    public static RestaurantTable toDomain(RestaurantTableEntity entity) {
        return RestaurantTable.builder()
                .id(entity.getId())
                .tableNumber(entity.getTableNumber())
                .capacity(entity.getCapacity())
                .isActive(entity.getIsActive())
                .build();
    }

    public static RestaurantTableEntity toEntity(RestaurantTable domain) {
        return RestaurantTableEntity.builder()
                .id(domain.getId())
                .tableNumber(domain.getTableNumber())
                .capacity(domain.getCapacity())
                .isActive(domain.getIsActive())
                .build();
    }
}
