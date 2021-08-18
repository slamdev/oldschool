package com.github.slamdev.oldschool.business.boundary;

import com.github.slamdev.oldschool.integration.HtmlUnitTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.assertj.core.api.Assertions.assertThat;

@HtmlUnitTest
@WebMvcTest(IndexController.class)
class IndexControllerTest {

    @Autowired
    private WebDriver webDriver;

    @Test
    void should_show_index_page() {
        webDriver.get("/");
        assertThat(webDriver.findElement(By.tagName("h1")).getText()).isEqualTo("Home page");
    }
}
