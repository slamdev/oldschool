package com.github.slamdev.oldschool.business.entity;

import lombok.Value;
import lombok.With;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Value
@With
@Table("product")
public class ProductModel {

    @Id
    UUID id;

    String name;

    String description;

    @Version
    Long version;

    @CreatedDate
    Instant createdDate;

    @LastModifiedDate
    Instant lastModifiedDate;
}
