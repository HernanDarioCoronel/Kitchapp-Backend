package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.PurchaseOrder;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.PurchaseOrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = SupplierMapper.class)
public interface PurchaseOrderMapper {
    @Mapping(target = "purchaseOrderLines", ignore = true)
    PurchaseOrder toDomain(PurchaseOrderEntity entity);

    PurchaseOrderEntity toEntity(PurchaseOrder domain);
}

