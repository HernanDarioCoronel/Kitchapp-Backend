package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "unit_types", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "unit_types_name_key",
                columnNames = {"name"}),
        @UniqueConstraint(name = "unit_types_abbreviation_key",
                columnNames = {"abbreviation"})})
public class UnitTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "abbreviation", nullable = false, length = 10)
    private String abbreviation;

}