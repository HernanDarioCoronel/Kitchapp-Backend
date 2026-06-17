package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.adapters;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.OrderConsumableItem;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.OrderConsumableItemRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories.OrderConsumableItemEntityRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories.OrderEntityRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping.OrderConsumableItemMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderConsumableItemAdapter implements OrderConsumableItemRepository {
    private final OrderConsumableItemEntityRepository jpaRepository;
    private final OrderEntityRepository orderEntityRepository;
    private final OrderConsumableItemMapper mapper;

    @Override
    public List<OrderConsumableItem> saveAll(List<OrderConsumableItem> items, UUID orderId) {
        var orderEntity = orderEntityRepository.getReferenceById(orderId);
        return items.stream()
                .map(item -> {
                    var entity = mapper.toEntity(item);
                    entity.setOrder(orderEntity);
                    return mapper.toDomain(jpaRepository.save(entity));
                })
                .toList();
    }

    @Transactional
    @Override
    public OrderConsumableItem patch(UUID id, OrderConsumableItem patch) {
        var entity = jpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("OrderConsumableItem no encontrado: " + id));
        Optional.ofNullable(patch.getDelivered()).ifPresent(entity::setDelivered);
        return mapper.toDomain(jpaRepository.save(entity));
    }
}
