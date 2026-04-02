package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Payment;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.PaymentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = OrderMapper.class)
public interface PaymentMapper {
    Payment toDomain(PaymentEntity entity);

    PaymentEntity toEntity(Payment domain);
}

