package com.dev.msscbeerservice.web.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Beer {



    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",strategy ="org.hibernate.id.UUIDGenerator")
    @Type(type="org.hibernate.type.UUIDCharType")
    @Column(length = 36, columnDefinition = "varchar(36)",updatable = false,nullable = false)

    private UUID id;
@Version
private Long version;
@CreationTimestamp
@Column(updatable = false)
private Timestamp createdDate;

@UpdateTimestamp
@Column(insertable = false)
private Timestamp lastModifiedDate;

private String beerName;
private String beerStyle;

@Column(unique = true)
    private String upc;

    private BigDecimal price;

    private Integer minOnHand;
    private Integer quantityToBrew;
}
