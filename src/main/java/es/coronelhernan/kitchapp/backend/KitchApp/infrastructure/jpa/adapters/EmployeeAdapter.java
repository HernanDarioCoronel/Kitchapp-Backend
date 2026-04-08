package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.adapters;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Employee;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.EmployeeRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories.EmployeeEntityRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EmployeeAdapter implements EmployeeRepository {
    private final EmployeeEntityRepository jpaRepository;
    private final EmployeeMapper mapper;

    @Override
    public Employee save(Employee domain) {
        var savedEntity = this.jpaRepository.save(this.mapper.toEntity(domain));
        return this.mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Employee> findById(UUID id) {
        return this.jpaRepository.findById(id)
                .map(this.mapper::toDomain);
    }

    @Override
    public List<Employee> findAll() {
        return this.jpaRepository.findAll().stream()
                .map(this.mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(Employee domain) {
        this.jpaRepository.deleteById(domain.getId());
    }
}

