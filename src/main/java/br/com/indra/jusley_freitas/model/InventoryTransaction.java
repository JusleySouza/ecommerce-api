package br.com.indra.jusley_freitas.model;

import br.com.indra.jusley_freitas.enums.InventoryReason;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inventory_transactions")
public class InventoryTransaction {
    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private UUID id;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "delta", nullable = false)
    private Integer delta;

    @Enumerated(EnumType.STRING)
    @Column(name = "reason", nullable = false)
    private InventoryReason reason;

    @Column(name = "reference_id")
    private String referenceID;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

}
