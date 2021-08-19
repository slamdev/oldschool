package com.github.slamdev.oldschool.business.boundary;

import com.github.slamdev.oldschool.business.control.ProductService;
import com.github.slamdev.oldschool.business.entity.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("cart")
@RequiredArgsConstructor
public class CartController {

    private static final String CART_SESSION_ATTR = "cart";

    private final ProductService productService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ModelAndView cartPage(HttpSession session) {
        List<UUID> productIds = getAddedProducts(session);
        List<ProductDto> products = productService.getProducts(productIds);
        return new ModelAndView("pages/cart", Map.of("products", products));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public String addToCart(RedirectAttributes redirectAttributes, @RequestParam UUID productId, HttpSession session) {
        List<UUID> productIds = getAddedProducts(session);
        productIds.add(productId);
        session.setAttribute(CART_SESSION_ATTR, productIds);
        redirectAttributes.addAttribute("id", productId);
        return "redirect:/products";
    }

    @SuppressWarnings("unchecked")
    private List<UUID> getAddedProducts(HttpSession session) {
        return Optional
                .ofNullable((List<UUID>) session.getAttribute(CART_SESSION_ATTR))
                .orElseGet(ArrayList::new);
    }
}
