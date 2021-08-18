package com.github.slamdev.oldschool.business.boundary;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String indexPage() {
        log.info("controller loaded");
        return "pages/index";
    }
}
