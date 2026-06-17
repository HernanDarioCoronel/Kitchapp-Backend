package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.adapters;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.OrderDish;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.OrderDishRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories.OrderDishEntityRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories.OrderEntityRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping.OrderDishMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderDishAdapter implements OrderDishRepository {
    private final OrderDishEntityRepository jpaRepository;
    private final OrderEntityRepository orderEntityRepository;
    private final OrderDishMapper mapper;

    @Override
    public List<OrderDish> saveAll(List<OrderDish> dishes, UUID orderId) {
        var orderEntity = orderEntityRepository.getReferenceById(orderId);
        return dishes.stream()
                .map(dish -> {
                    var entity = mapper.toEntity(dish);
                    entity.setOrder(orderEntity);
                    return mapper.toDomain(jpaRepository.save(entity));
                })
                .toList();
    }

    @Transactional
    @Override
    public OrderDish patch(UUID id, OrderDish patch) {
        var entity = jpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("OrderDish no encontrado: " + id));
        Optional.ofNullable(patch.getStatus()).ifPresent(entity::setStatus);
        return mapper.toDomain(jpaRepository.save(entity));
    }
}
