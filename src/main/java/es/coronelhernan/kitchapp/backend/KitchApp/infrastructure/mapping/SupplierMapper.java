package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.enums.DeliveryDays;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Supplier;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.SupplierEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    @Mapping(target = "days", source = "days", qualifiedByName = "arrayToSingleDay")
    Supplier toDomain(SupplierEntity entity);

    @Mapping(target = "days", source = "days", qualifiedByName = "singleDayToArray")
    SupplierEntity toEntity(Supplier domain);

    @Named("arrayToSingleDay")
    default DeliveryDays arrayToSingleDay(DeliveryDays[] days) {
        return days != null && days.length > 0 ? days[0] : null;
    }

    @Named("singleDayToArray")
    default DeliveryDays[] singleDayToArray(DeliveryDays day) {
        return day != null ? new DeliveryDays[]{day} : null;
    }
}

