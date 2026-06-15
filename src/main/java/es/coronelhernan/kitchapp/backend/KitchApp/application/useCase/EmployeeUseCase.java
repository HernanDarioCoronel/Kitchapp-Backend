package es.coronelhernan.kitchapp.backend.KitchApp.application.useCase;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Employee;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import es.coronelhernan.kitchapp.backend.KitchApp.application.utils.PatchUtils;

@RequiredArgsConstructor
@Service
public class EmployeeUseCase {
    private final EmployeeRepository repository;

    @Transactional
    public Employee save(Employee employee) {
        return this.repository.save(employee.toBuilder().id(null).build());
    }

    @Transactional
    public Optional<Employee> findById(UUID id) {
        return this.repository.findById(id);
    }

    @Transactional
    public List<Employee> findAll() {
        return this.repository.findAll();
    }

    @Transactional
    public void delete(UUID id) {
        var employee = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));
        this.repository.delete(employee);
    }

    @Transactional
    public Employee update(UUID id, Employee patch) {
        var employee = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));
        PatchUtils.copyNonNullProperties(patch, employee, "id", "createdAt");
        return this.repository.save(employee);
    }
}

