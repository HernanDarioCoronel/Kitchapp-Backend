package es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.enums.ProductType;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends BaseRepository<Product> {
    List<Product> findAllByType(ProductType type);
}
