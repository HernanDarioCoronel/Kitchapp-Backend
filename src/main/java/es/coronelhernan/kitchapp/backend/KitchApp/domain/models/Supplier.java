package es.coronelhernan.kitchapp.backend.KitchApp.domain.models;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.enums.DeliveryDays;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.enums.SupplierType;
import lombok.*;

import java.util.UUID;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Supplier {
    @EqualsAndHashCode.Include
    private UUID id;
    private String nif;
    private String tradeName;
    private String businessName;
    private Boolean reEquivalence;
    private SupplierType type;
    private DeliveryDays days;
    private String email;
    private String phone1;
    private String phone2;
    private String iban;
    private String rgseaaNumber;
}