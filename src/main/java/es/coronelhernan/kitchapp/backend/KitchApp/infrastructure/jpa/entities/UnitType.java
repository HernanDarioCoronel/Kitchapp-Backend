package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "unit_types", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "unit_types_name_key",
                columnNames = {"name"}),
        @UniqueConstraint(name = "unit_types_abbreviation_key",
                columnNames = {"abbreviation"})})
public class UnitType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "abbreviation", nullable = false, length = 10)
    private String abbreviation;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

}