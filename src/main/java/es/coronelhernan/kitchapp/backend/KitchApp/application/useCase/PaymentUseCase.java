package es.coronelhernan.kitchapp.backend.KitchApp.application.useCase;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Payment;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import es.coronelhernan.kitchapp.backend.KitchApp.application.utils.PatchUtils;

@RequiredArgsConstructor
@Service
public class PaymentUseCase {
    private final PaymentRepository repository;

    @Transactional
    public Payment save(Payment payment) {
        return this.repository.save(payment);
    }

    @Transactional
    public Optional<Payment> findById(UUID id) {
        return this.repository.findById(id);
    }

    @Transactional
    public List<Payment> findAll() {
        return this.repository.findAll();
    }

    @Transactional
    public void delete(UUID id) {
        var payment = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado"));
        this.repository.delete(payment);
    }

    @Transactional
    public Payment update(UUID id, Payment patch) {
        var payment = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado"));
        PatchUtils.copyNonNullProperties(patch, payment, "id", "createdAt");
        return this.repository.save(payment);
    }
}

