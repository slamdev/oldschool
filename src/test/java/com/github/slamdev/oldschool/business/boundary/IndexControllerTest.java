package com.github.slamdev.oldschool.business.boundary;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.github.slamdev.oldschool.integration.SecurityConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.web.htmlunit.webdriver.LocalHostWebConnectionHtmlUnitDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.webdriver.MockMvcHtmlUnitDriverBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IndexController.class)
@Import({SecurityConfig.class, IndexControllerTest.WD.class})
@Slf4j
class IndexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebDriver webDriver;

//    @BeforeAll
//    public static void setupClass() {
//        WebDriverManager.chromedriver().setup();
//    }

    @Test
    void should_show_index_page() throws Exception {
        webDriver.get("http://localhost:80/");
        assertThat(webDriver.findElement(By.tagName("h1")).getText()).isEqualTo("Car");

//        log.info("{}: {}", webDriver.getTitle(), webDriver.getPageSource());
        mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Lorem Ipsum")));
    }

//    @Configuration
    public static class WD {
        @Bean
        public MockMvcHtmlUnitDriverBuilder mockMvcHtmlUnitDriverBuilder(MockMvc mockMvc, Environment environment) {
            return MockMvcHtmlUnitDriverBuilder.mockMvcSetup(mockMvc)
                    .withDelegate(new LocalHostWebConnectionHtmlUnitDriver(environment, BrowserVersion.CHROME))
                    .javascriptEnabled(true);
        }
    }
}