package com.github.slamdev.oldschool.business.boundary;

import com.github.slamdev.oldschool.integration.HtmlUnitTest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.assertj.core.api.Assertions.assertThat;

@HtmlUnitTest
@WebMvcTest(IndexController.class)
class IndexControllerTest {

    @Autowired
    private WebDriver webDriver;

    private IndexPage page;

    @BeforeEach
    void initPage() {
        page = PageFactory.initElements(webDriver, IndexPage.class);
    }

    @Test
    void should_show_index_page() {
        page.open();
        assertThat(page.header.getText()).isEqualTo("Home page");
    }

    @RequiredArgsConstructor
    public static class IndexPage {

        private final WebDriver webDriver;

        @FindBy(tagName = "h1")
        WebElement header;

        void open() {
            webDriver.get("/");
        }
    }
}
