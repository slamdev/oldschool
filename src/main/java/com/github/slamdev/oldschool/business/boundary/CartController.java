package com.github.slamdev.oldschool.business.boundary;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("cart")
public class CartController {

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public String addToCart(RedirectAttributes redirectAttributes, @RequestParam UUID productId) {
        log.info("product {} added to the cart", productId);
        redirectAttributes.addAttribute("id", productId);
        return "redirect:/products/details";
    }
}
