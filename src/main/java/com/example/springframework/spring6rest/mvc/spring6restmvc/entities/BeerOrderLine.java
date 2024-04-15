package com.example.springframework.spring6rest.mvc.spring6restmvc.entities;
/*
 * @Author tmekaumput
 * @Date 15/4/24 6:51 pm
 *
 */

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerOrderLine {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, columnDefinition = "varchar(36)", nullable = false, updatable = false)
    @JdbcTypeCode(SqlTypes.CHAR)
    private String id;

    @Version
    private Long version;

    @NotNull
    private Integer orderQuantity;

    @NotNull
    private Integer quantityAllocated;

    @ManyToOne
    private Beer beer;

    @ManyToOne
    private BeerOrder beerOrder;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime lastModifiedDate;
}
