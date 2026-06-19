package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Product;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, UnitTypeMapper.class, AllergenMapper.class})
public interface ProductMapper {
    Product toDomain(ProductEntity entity);

    ProductEntity toEntity(Product domain);
}

