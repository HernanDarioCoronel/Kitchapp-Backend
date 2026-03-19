package es.coronelhernan.kitchapp.backend.KitchApp.domain.models;

import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Allergen {
    @EqualsAndHashCode.Include
    private UUID id;
    private String name;
    private String description;
    private Set<Product> product;
}