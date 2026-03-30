package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.adapters;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.TableOccupation;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.TableOccupationRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories.TableOccupationEntityRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping.TableOccupationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TableOccupationAdapter implements TableOccupationRepository {
    private final TableOccupationEntityRepository jpaRepository;
    private final TableOccupationMapper mapper;

    @Override
    public TableOccupation save(TableOccupation domain) {
        var entity = this.mapper.toEntity(domain);
        var savedEntity = this.jpaRepository.save(entity);
        return this.mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<TableOccupation> findById(UUID id) {
        return this.jpaRepository.findById(id)
                .map(this.mapper::toDomain);
    }

    @Override
    public List<TableOccupation> findAll() {
        return this.jpaRepository.findAll().stream()
                .map(this.mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(TableOccupation domain) {
        this.jpaRepository.delete(this.mapper.toEntity(domain));
    }

    @Override
    public Optional<TableOccupation> findOpenByTableId(UUID tableId) {
        return this.jpaRepository.findByTableIdAndEndedAtIsNull(tableId)
                .map(this.mapper::toDomain);
    }
}
