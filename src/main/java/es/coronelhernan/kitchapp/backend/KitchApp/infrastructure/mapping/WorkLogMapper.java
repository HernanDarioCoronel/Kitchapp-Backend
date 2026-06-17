package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.WorkLog;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.WorkLogEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface WorkLogMapper {
    WorkLog toDomain(WorkLogEntity entity);
    WorkLogEntity toEntity(WorkLog domain);
}
