package es.coronelhernan.kitchapp.backend.KitchApp.domain.models;

import lombok.*;

import java.util.UUID;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UnitType {
    @EqualsAndHashCode.Include
    private UUID id;
    private String name;
    private String abbreviation;
}