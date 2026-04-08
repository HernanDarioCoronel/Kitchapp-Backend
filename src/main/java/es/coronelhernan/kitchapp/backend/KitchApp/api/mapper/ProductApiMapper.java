package es.coronelhernan.kitchapp.backend.KitchApp.api.mapper;

import es.coronelhernan.kitchapp.backend.KitchApp.api.dto.product.ProductRequest;
import es.coronelhernan.kitchapp.backend.KitchApp.api.dto.product.ProductResponse;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Allergen;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Category;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Product;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.UnitType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ProductApiMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "category", expression = "java(toCategory(request.categoryId()))")
    @Mapping(target = "unitType", expression = "java(toUnitType(request.unitTypeId()))")
    @Mapping(target = "allergens", expression = "java(toAllergens(request.allergenIds()))")
    Product toDomain(ProductRequest request);

    @Mapping(target = "categoryId", expression = "java(product.getCategory() == null ? null : product.getCategory().getId())")
    @Mapping(target = "unitTypeId", expression = "java(product.getUnitType() == null ? null : product.getUnitType().getId())")
    @Mapping(target = "allergenIds", expression = "java(toAllergenIds(product.getAllergens()))")
    ProductResponse toResponse(Product product);

    default Category toCategory(UUID categoryId) {
        return categoryId == null ? null : Category.builder().id(categoryId).build();
    }

    default UnitType toUnitType(UUID unitTypeId) {
        return unitTypeId == null ? null : UnitType.builder().id(unitTypeId).build();
    }

    default Set<Allergen> toAllergens(Set<UUID> allergenIds) {
        if (allergenIds == null) {
            return null;
        }
        return allergenIds.stream()
                .map(id -> Allergen.builder().id(id).build())
                .collect(java.util.stream.Collectors.toSet());
    }

    default Set<UUID> toAllergenIds(Set<Allergen> allergens) {
        if (allergens == null) {
            return null;
        }
        return allergens.stream()
                .map(Allergen::getId)
                .collect(java.util.stream.Collectors.toSet());
    }
}

