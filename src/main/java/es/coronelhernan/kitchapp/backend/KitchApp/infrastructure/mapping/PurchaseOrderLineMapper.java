package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.PurchaseOrderLine;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.PurchaseOrderLineEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PurchaseOrderMapper.class, ProductMapper.class, TaxMapper.class})
public interface PurchaseOrderLineMapper {
    PurchaseOrderLine toDomain(PurchaseOrderLineEntity entity);

    PurchaseOrderLineEntity toEntity(PurchaseOrderLine domain);
}

