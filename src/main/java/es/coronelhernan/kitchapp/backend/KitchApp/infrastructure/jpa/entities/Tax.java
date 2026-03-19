package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "taxes", schema = "public")
public class Tax {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "value", nullable = false, precision = 2, scale = 3)
    private BigDecimal value;

}