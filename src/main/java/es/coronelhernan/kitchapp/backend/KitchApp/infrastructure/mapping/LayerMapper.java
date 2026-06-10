package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Layer;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.LayerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LayerMapper {
    Layer toDomain(LayerEntity entity);
    LayerEntity toEntity(Layer domain);
}
