package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Order;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TableOccupationMapper.class, EmployeeMapper.class, OrderDishMapper.class, OrderConsumableItemMapper.class})
public interface OrderMapper {
    Order toDomain(OrderEntity entity);

    @Mapping(target = "orderDishes", ignore = true)
    @Mapping(target = "orderConsumableItems", ignore = true)
    OrderEntity toEntity(Order domain);
}

