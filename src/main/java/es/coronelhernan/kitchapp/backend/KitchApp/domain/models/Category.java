package es.coronelhernan.kitchapp.backend.KitchApp.domain.models;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.enums.CategoryType;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Category {
    @EqualsAndHashCode.Include
    private UUID id;
    private String name;
    private String description;
    private CategoryType type;
    private Boolean active;
    private OffsetDateTime createdAt;
}
