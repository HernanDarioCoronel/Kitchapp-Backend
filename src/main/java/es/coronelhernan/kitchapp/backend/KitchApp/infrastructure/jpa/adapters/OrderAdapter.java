package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.adapters;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Order;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.OrderRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories.OrderEntityRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderAdapter implements OrderRepository {
    private final OrderEntityRepository jpaRepository;
    private final OrderMapper mapper;

    @Override
    public Order save(Order domain) {
        var savedEntity = this.jpaRepository.save(this.mapper.toEntity(domain));
        return this.mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return this.jpaRepository.findById(id)
                .map(this.mapper::toDomain);
    }

    @Override
    public List<Order> findAll() {
        return this.jpaRepository.findAll().stream()
                .map(this.mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(Order domain) {
        this.jpaRepository.deleteById(domain.getId());
    }
}

