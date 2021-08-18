package com.github.slamdev.oldschool.business.control;

import com.github.slamdev.oldschool.business.entity.ProductDto;
import com.github.slamdev.oldschool.business.entity.ProductModel;
import com.github.slamdev.oldschool.integration.Streams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public List<ProductDto> getAllProducts() {
        return Streams.stream(repository.findAll())
                .peek(m -> log.info("{}", m))
                .map(this::asDto)
                .collect(Collectors.toList());
    }

    public Optional<ProductDto> getById(UUID id) {
        return repository.findById(id).map(this::asDto);
    }

    private ProductDto asDto(ProductModel model) {
        return ProductDto.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .image(randomImage())
                .build();
    }

    private String randomImage() {
        double min = 1;
        double max = 5;
        int rnd = (int) (Math.random() * (max - min) + min);
        return String.format("https://www.gstatic.com/webp/gallery/%d.jpg", rnd);
    }
}
