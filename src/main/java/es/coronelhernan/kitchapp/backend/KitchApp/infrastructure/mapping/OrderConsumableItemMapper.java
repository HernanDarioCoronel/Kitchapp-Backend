package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.OrderConsumableItem;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.OrderConsumableItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface OrderConsumableItemMapper {
    OrderConsumableItem toDomain(OrderConsumableItemEntity entity);

    @Mapping(target = "order", ignore = true)
    OrderConsumableItemEntity toEntity(OrderConsumableItem domain);
}

