package es.coronelhernan.kitchapp.backend.KitchApp.api.mapper;

import es.coronelhernan.kitchapp.backend.KitchApp.api.dto.worklog.WorkLogRequest;
import es.coronelhernan.kitchapp.backend.KitchApp.api.dto.worklog.WorkLogResponse;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Employee;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.WorkLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface WorkLogApiMapper {

    @Mapping(target = "employee", expression = "java(toStubEmployee(request.employeeId()))")
    @Mapping(target = "createdAt", ignore = true)
    WorkLog toDomain(WorkLogRequest request);

    @Mapping(target = "employee", expression = "java(toEmployeeInfo(workLog.getEmployee()))")
    WorkLogResponse toResponse(WorkLog workLog);

    default Employee toStubEmployee(UUID employeeId) {
        return Employee.builder().id(employeeId).build();
    }

    default WorkLogResponse.EmployeeInfo toEmployeeInfo(Employee employee) {
        if (employee == null) return null;
        return new WorkLogResponse.EmployeeInfo(
                employee.getId(),
                employee.getFullName(),
                employee.getRole() != null ? employee.getRole().name() : null,
                employee.getIsActive()
        );
    }
}
