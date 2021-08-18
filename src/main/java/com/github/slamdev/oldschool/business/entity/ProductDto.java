package com.github.slamdev.oldschool.business.entity;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class ProductDto {
    UUID id;
    String name;
    String description;
    String image;
}
