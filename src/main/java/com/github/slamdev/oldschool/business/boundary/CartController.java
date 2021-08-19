package com.github.slamdev.oldschool.business.boundary;

import com.github.slamdev.oldschool.business.control.CartService;
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
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ModelAndView cartPage(HttpSession session) {
        List<ProductDto> products = cartService.getCartProducts(session);
        return new ModelAndView("pages/cart", Map.of("products", products));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public String addToCart(RedirectAttributes redirectAttributes, @RequestParam UUID productId, HttpSession session) {
        cartService.addProduct(session, productId);
        redirectAttributes.addAttribute("id", productId);
        return "redirect:/products";
    }
}
