package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

@Entity
@Table(name = "suppliers", schema = "public")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Boolean getReEquivalence() {
        return reEquivalence;
    }

    public void setReEquivalence(Boolean reEquivalence) {
        this.reEquivalence = reEquivalence;
    }

    public SupplierType getType() {
        return type;
    }

    public void setType(SupplierType type) {
        this.type = type;
    }

    public DeliveryDays getDays() {
        return days;
    }

    public void setDays(DeliveryDays days) {
        this.days = days;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getRgseaaNumber() {
        return rgseaaNumber;
    }

    public void setRgseaaNumber(String rgseaaNumber) {
        this.rgseaaNumber = rgseaaNumber;
    }

    public enum SupplierType {
        PERISHABLES,
        DRINKS,
        KITCHENWARE,
        APPLIANCES,
        SERVICES
    }

    public enum DeliveryDays {
        MON,
        TUE,
        WED,
        THU,
        FRI,
        ORD,
        VAR
    }
}