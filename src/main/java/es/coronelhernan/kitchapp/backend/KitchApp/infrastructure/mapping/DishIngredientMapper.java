package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.DishIngredient;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.DishIngredientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface DishIngredientMapper {
    DishIngredient toDomain(DishIngredientEntity entity);

    @Mapping(target = "dish", ignore = true)
    @Mapping(target = "isOptional", expression = "java(domain.isOptional())")
    DishIngredientEntity toEntity(DishIngredient domain);
}
