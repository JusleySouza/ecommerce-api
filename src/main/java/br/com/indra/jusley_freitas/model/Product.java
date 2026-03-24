package br.com.indra.jusley_freitas.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products", schema = "JP_CAPACITACAO")
public class Product {

    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private UUID id;

    @Column(name = "name",  nullable = false)
    private String name;

    @Column(name = "description",   nullable = false)
    private String description;

    @Column(name = "sku", nullable = false, unique = true, updatable = false)
    private String sku;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "cost_price", nullable = false)
    private BigDecimal costPrice;

    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "created", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "idCategory")
    private Category category;

}
