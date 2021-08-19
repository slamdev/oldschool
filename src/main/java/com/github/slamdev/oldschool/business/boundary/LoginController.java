package com.github.slamdev.oldschool.business.boundary;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("login")
public class LoginController {

    @GetMapping
    @PreAuthorize("isAnonymous()")
    public String loginPage() {
        return "pages/login";
    }
}
