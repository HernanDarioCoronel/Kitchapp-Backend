package es.coronelhernan.kitchapp.backend.KitchApp.application.useCase;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Order;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderUseCase {
    private final OrderRepository repository;

    @Transactional
    public Order save(Order order) {
        return this.repository.save(order);
    }

    @Transactional
    public Optional<Order> findById(UUID id) {
        return this.repository.findById(id);
    }

    @Transactional
    public List<Order> findAll() {
        return this.repository.findAll();
    }

    @Transactional
    public void delete(UUID id) {
        var order = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));
        this.repository.delete(order);
    }
}

