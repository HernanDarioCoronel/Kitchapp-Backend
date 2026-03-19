package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.enums.DeliveryDays;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.enums.SupplierType;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "suppliers", schema = "public")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "nif", nullable = false, length = 9)
    private String nif;

    @Column(name = "trade_name", length = 100)
    private String tradeName;

    @Column(name = "business_name", nullable = false, length = 100)
    private String businessName;

    @ColumnDefault("false")
    @Column(name = "re_equivalence")
    private Boolean reEquivalence;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition = "supplier_type not null")
    private SupplierType type;

    @ColumnDefault("'{VAR}'")
    @Enumerated(EnumType.STRING)
    @Column(name = "days", columnDefinition = "delivery_days[]")
    private DeliveryDays days;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "phone_1", length = 20)
    private String phone1;

    @Column(name = "phone_2", length = 20)
    private String phone2;

    @Column(name = "iban", length = 22)
    private String iban;

    @Column(name = "rgseaa_number", length = Integer.MAX_VALUE)
    private String rgseaaNumber;

}