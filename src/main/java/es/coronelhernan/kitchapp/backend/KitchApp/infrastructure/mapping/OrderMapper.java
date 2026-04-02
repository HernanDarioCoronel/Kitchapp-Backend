package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Order;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TableOccupationMapper.class, EmployeeMapper.class})
public interface OrderMapper {
    @Mapping(target = "orderDishes", ignore = true)
    @Mapping(target = "orderConsumableItems", ignore = true)
    Order toDomain(OrderEntity entity);

    OrderEntity toEntity(Order domain);
}

