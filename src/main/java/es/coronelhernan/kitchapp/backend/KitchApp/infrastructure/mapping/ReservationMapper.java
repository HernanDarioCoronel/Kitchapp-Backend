package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Reservation;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.ReservationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = RestaurantTableMapper.class)
public interface ReservationMapper {
    Reservation toDomain(ReservationEntity entity);

    ReservationEntity toEntity(Reservation domain);
}

