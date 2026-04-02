package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Product;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, UnitTypeMapper.class})
public interface ProductMapper {
    @Mapping(target = "allergens", ignore = true)
    Product toDomain(ProductEntity entity);

    ProductEntity toEntity(Product domain);
}

