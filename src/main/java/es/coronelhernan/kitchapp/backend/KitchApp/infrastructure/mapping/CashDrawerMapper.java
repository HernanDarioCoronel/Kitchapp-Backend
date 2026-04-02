package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.CashDrawer;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.CashDrawerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = EmployeeMapper.class)
public interface CashDrawerMapper {
    CashDrawer toDomain(CashDrawerEntity entity);

    CashDrawerEntity toEntity(CashDrawer domain);
}

