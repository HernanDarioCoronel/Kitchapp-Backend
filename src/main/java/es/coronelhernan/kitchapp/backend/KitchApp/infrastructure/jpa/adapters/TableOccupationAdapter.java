package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.adapters;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.TableOccupation;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.TableOccupationRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories.TableOccupationEntityRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping.TableOccupationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TableOccupationAdapter implements TableOccupationRepository {

    private final TableOccupationEntityRepository jpaRepository;

    @Override
    public TableOccupation save(TableOccupation occupation) {
        return TableOccupationMapper.toDomain(
                this.jpaRepository.save(
                        TableOccupationMapper.toEntity(occupation)
                )
        );
    }

    @Override
    public Optional<TableOccupation> findById(UUID id) {
        return this.jpaRepository.findById(id)
                .map(TableOccupationMapper::toDomain);
    }

    @Override
    public List<TableOccupation> findAll() {
        List<TableOccupation> occupations = new ArrayList<>();
        this.jpaRepository.findAll()
                .forEach(entity ->
                        occupations.add(TableOccupationMapper.toDomain(entity))
                );
        return occupations;
    }

    @Override
    public void delete(TableOccupation entity) {
        this.jpaRepository.delete(TableOccupationMapper.toEntity(entity));
    }

    @Override
    public Optional<TableOccupation> findOpenByTableId(UUID tableId) {
        return this.jpaRepository.findByTableIdAndEndedAtIsNull(tableId)
                .map(TableOccupationMapper::toDomain);
    }
}
