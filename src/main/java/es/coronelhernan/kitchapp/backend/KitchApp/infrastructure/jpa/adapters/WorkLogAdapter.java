package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.adapters;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.enums.WorkLogType;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.WorkLog;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.WorkLogRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.WorkLogEntity;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories.WorkLogEntityRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping.WorkLogMapper;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WorkLogAdapter implements WorkLogRepository {

    private final WorkLogEntityRepository jpaRepository;
    private final WorkLogMapper mapper;

    @Override
    public WorkLog save(WorkLog domain) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(domain)));
    }

    @Override
    public Optional<WorkLog> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<WorkLog> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void delete(WorkLog domain) {
        jpaRepository.deleteById(domain.getId());
    }

    @Override
    public List<WorkLog> findFiltered(UUID employeeId, OffsetDateTime from, OffsetDateTime to, WorkLogType type) {
        Specification<WorkLogEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (employeeId != null) {
                predicates.add(cb.equal(root.get("employee").get("id"), employeeId));
            }
            if (from != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("timestamp"), from));
            }
            if (to != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("timestamp"), to));
            }
            if (type != null) {
                predicates.add(cb.equal(root.get("type"), type));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return jpaRepository.findAll(spec, Sort.by(Sort.Direction.DESC, "timestamp")).stream()
                .map(mapper::toDomain)
                .toList();
    }
}
