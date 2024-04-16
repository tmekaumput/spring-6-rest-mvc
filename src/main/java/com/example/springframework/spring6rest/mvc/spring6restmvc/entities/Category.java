package com.example.springframework.spring6rest.mvc.spring6restmvc.entities;
/*
 * @Author tmekaumput
 * @Date 16/4/24 10:56 am
 *
 */

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID id;

    @Version
    private Long version;

    @Size(max = 50)
    @Column(length = 50)
    private String description;

    @ManyToMany
    @JoinTable(name = "beer_category",
    joinColumns = @JoinColumn(name = "category_id"),
    inverseJoinColumns = @JoinColumn(name = "beer_id"))
    private Set<Beer> beers;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime lastModifiedDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) && Objects.equals(version, category.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version);
    }
}
