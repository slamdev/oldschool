package com.github.slamdev.oldschool.business.boundary;

import com.github.slamdev.oldschool.business.control.CartService;
import com.github.slamdev.oldschool.business.entity.ProductDto;
import com.github.slamdev.oldschool.integration.HtmlUnitTest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@HtmlUnitTest
@WebMvcTest({CartController.class, LoginController.class})
class CartControllerTest {

    @Autowired
    private WebDriver webDriver;

    @MockBean
    private CartService cartService;

    private CartPage page;

    @BeforeEach
    void initPage() {
        page = PageFactory.initElements(webDriver, CartPage.class);
    }

    @Test
    void should_forward_to_login_for_unauthorized() {
        page.open();
        assertThat(webDriver.getTitle()).isEqualTo("Login Page");
    }

    @Test
    @WithMockUser
    void should_display_no_product() {
        when(cartService.getCartProducts(any())).thenReturn(Collections.emptyList());
        page.open();
        assertThat(page.products).isEmpty();
    }

    @Test
    @WithMockUser
    void should_display_products() {
        when(cartService.getCartProducts(any())).thenReturn(Collections.singletonList(
                ProductDto.builder()
                        .name("p1")
                        .image("http://test/img")
                        .description("desc")
                        .build())
        );
        page.open();
        assertThat(page.products).hasSize(1);
        WebElement product = page.products.get(0);
        assertThat(page.productImage(product).getAttribute("src")).isEqualTo("http://test/img");
        assertThat(page.productName(product).getText()).isEqualTo("p1");
        assertThat(page.productDescription(product).getText()).isEqualTo("desc");
    }

    @RequiredArgsConstructor
    public static class CartPage {

        private final WebDriver webDriver;

        @FindBy(className = "card")
        List<WebElement> products;

        void open() {
            webDriver.get("/cart");
        }

        WebElement productImage(WebElement product) {
            return product.findElement(By.className("img-fluid"));
        }

        WebElement productName(WebElement product) {
            return product.findElement(By.className("card-title"));
        }

        WebElement productDescription(WebElement product) {
            return product.findElement(By.className("card-text"));
        }
    }
}
