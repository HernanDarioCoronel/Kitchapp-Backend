package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories;

import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.DishIngredientEntity;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DishIngredientEntityRepository extends JpaRepository<DishIngredientEntity, UUID> {

    @Query("select di from DishIngredientEntity di " +
            "join fetch di.product p " +
            "join fetch di.dish d " +
            "where d.id in :dishIds")
    List<DishIngredientEntity> findByDishIdsWithProductAndDish(@Param("dishIds") List<UUID> dishIds);
}
