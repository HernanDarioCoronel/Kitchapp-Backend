package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.OrderDish;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.OrderDishEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = DishMapper.class)
public interface OrderDishMapper {
    OrderDish toDomain(OrderDishEntity entity);

    @Mapping(target = "order", ignore = true)
    OrderDishEntity toEntity(OrderDish domain);
}

