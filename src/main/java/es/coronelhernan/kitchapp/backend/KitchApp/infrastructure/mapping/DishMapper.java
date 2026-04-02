package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Dish;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.DishEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CategoryMapper.class)
public interface DishMapper {
    @Mapping(target = "dishIngredientList", ignore = true)
    Dish toDomain(DishEntity entity);

    DishEntity toEntity(Dish domain);
}

