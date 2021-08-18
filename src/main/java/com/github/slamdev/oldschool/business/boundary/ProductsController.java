package com.github.slamdev.oldschool.business.boundary;

import com.github.slamdev.oldschool.business.control.ProductService;
import com.github.slamdev.oldschool.business.entity.ProductDto;
import com.github.slamdev.oldschool.integration.ErrorModels;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("products")
public class ProductsController {

    private final ProductService productService;

    @GetMapping
    @Secured("ROLE_USER")
    public ModelAndView productsPage() {
        List<ProductDto> products = productService.getAllProducts();
        return new ModelAndView("pages/products", Map.of("products", products));
    }

    @GetMapping("details")
    public ModelAndView detailsPage(@RequestParam UUID id) {
        Optional<ProductDto> product = productService.getById(id);
        return product
                .map(p -> new ModelAndView("pages/product-details", Map.of("product", p)))
                .orElseGet(() -> ErrorModels.clientError(HttpStatus.NOT_FOUND));
    }
}
