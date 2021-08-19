package com.github.slamdev.oldschool.business.control;

import com.github.slamdev.oldschool.business.entity.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CartService {

    private static final String CART_SESSION_ATTR = "cart";

    private final ProductService productService;

    public List<ProductDto> getCartProducts(HttpSession session) {
        List<UUID> productIds = getAddedProducts(session);
        return productService.getProducts(productIds);
    }

    public void addProduct(HttpSession session, UUID productId) {
        List<UUID> productIds = getAddedProducts(session);
        productIds.add(productId);
        session.setAttribute(CART_SESSION_ATTR, productIds);
    }

    @SuppressWarnings("unchecked")
    private List<UUID> getAddedProducts(HttpSession session) {
        return Optional
                .ofNullable((List<UUID>) session.getAttribute(CART_SESSION_ATTR))
                .orElseGet(ArrayList::new);
    }
}
