package es.coronelhernan.kitchapp.backend.KitchApp.domain.models;

import lombok.*;

import java.util.UUID;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Layer {
    @EqualsAndHashCode.Include
    private UUID id;
    private String name;
    private String shape;
}
