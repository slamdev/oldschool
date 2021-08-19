package com.github.slamdev.oldschool.business.boundary;

import com.github.slamdev.oldschool.business.control.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("register")
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("isAnonymous()")
    public String registerPage() {
        return "pages/register";
    }

    @PostMapping
    @PreAuthorize("isAnonymous()")
    public ModelAndView register(@RequestParam String email,
                                 @RequestParam String password,
                                 HttpServletRequest request) throws ServletException {
        HttpStatus result = userService.createUser(email, password);
        if (result.isError()) {
            return new ModelAndView("redirect:/register", Map.of("error", true));
        }
        request.login(email, password);
        return new ModelAndView("redirect:/");
    }
}
