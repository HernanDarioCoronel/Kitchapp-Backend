package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Stock;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.StockEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface StockMapper {
    Stock toDomain(StockEntity entity);

    StockEntity toEntity(Stock domain);
}

