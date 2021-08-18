package com.github.slamdev.oldschool.business.control;

import com.github.slamdev.oldschool.business.entity.ProductModel;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ProductRepository extends PagingAndSortingRepository<ProductModel, UUID> {
}
