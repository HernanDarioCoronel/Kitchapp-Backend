package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "dishes", schema = "public", uniqueConstraints = {@UniqueConstraint(name = "dishes_name_key",
        columnNames = {"name"})})
public class DishEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "prep_time", nullable = false, precision = 5, scale = 2)
    private BigDecimal prepTime;

    @Column(name = "price", precision = 8, scale = 2)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "dish_category_id", nullable = false)
    private CategoryEntity dishCategory;

    @ColumnDefault("true")
    @Column(name = "is_available")
    private Boolean isAvailable;

    @Column(name = "image_url", length = Integer.MAX_VALUE)
    private String imageUrl;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private OffsetDateTime createdAt;
}