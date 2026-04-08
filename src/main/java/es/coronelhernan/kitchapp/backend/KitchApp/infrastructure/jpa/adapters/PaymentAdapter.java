package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.adapters;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Payment;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.PaymentRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories.PaymentEntityRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentAdapter implements PaymentRepository {
    private final PaymentEntityRepository jpaRepository;
    private final PaymentMapper mapper;

    @Override
    public Payment save(Payment domain) {
        var savedEntity = this.jpaRepository.save(this.mapper.toEntity(domain));
        return this.mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Payment> findById(UUID id) {
        return this.jpaRepository.findById(id)
                .map(this.mapper::toDomain);
    }

    @Override
    public List<Payment> findAll() {
        return this.jpaRepository.findAll().stream()
                .map(this.mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(Payment domain) {
        this.jpaRepository.deleteById(domain.getId());
    }
}

