package br.com.indra.jusley_freitas.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sub_categories", schema = "JP_CAPACITACAO")
public class SubCategory {

    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "created", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "idCategory")
    private Category category;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "idSubCategory")
    private List<Product> listProducts;

}
