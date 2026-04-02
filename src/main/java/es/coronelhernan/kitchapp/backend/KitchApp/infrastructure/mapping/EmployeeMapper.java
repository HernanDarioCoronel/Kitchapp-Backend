package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Employee;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.EmployeeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    Employee toDomain(EmployeeEntity entity);

    EmployeeEntity toEntity(Employee domain);
}

