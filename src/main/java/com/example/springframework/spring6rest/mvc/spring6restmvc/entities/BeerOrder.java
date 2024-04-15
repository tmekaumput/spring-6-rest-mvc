package com.example.springframework.spring6rest.mvc.spring6restmvc.entities;
/*
 * @Author tmekaumput
 * @Date 15/4/24 4:30 pm
 *
 */

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BeerOrder {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    @JdbcTypeCode(SqlTypes.CHAR)
    private String id;

    @Version
    private Long version;

    @NotNull
    @NotBlank
    @Size(max = 255)
    @Column(length = 255)
    private String customerRef;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "beerOrder")
    private Set<BeerOrderLine> beerOrderLines;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime lastModifiedDate;


    @Override
    public String toString() {
        return "BeerOrder{" +
                "id='" + id + '\'' +
                ", version=" + version +
                ", customerRef='" + customerRef + '\'' +
                ", customer=" + customer +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeerOrder beerOrder = (BeerOrder) o;
        return Objects.equals(id, beerOrder.id) && Objects.equals(version, beerOrder.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version);
    }
}
