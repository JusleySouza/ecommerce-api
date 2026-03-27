package br.com.indra.jusley_freitas.model;

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
@Table(name = "users", schema = "JP_CAPACITACAO")
public class User {

    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private UUID id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "cpf", updatable = false, nullable = false, unique = true)
    private String cpf;

    @Column(name = "email",nullable = false, unique = true)
    private String email;

    @Column(name = "created", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated")
    private LocalDateTime updatedAt;
}
