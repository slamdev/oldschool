package com.github.slamdev.oldschool.integration;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.javascript.SilentJavaScriptErrorListener;
import org.springframework.boot.test.web.htmlunit.webdriver.LocalHostWebConnectionHtmlUnitDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.webdriver.MockMvcHtmlUnitDriverBuilder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({SecurityConfig.class, HtmlUnitTest.HtmlUnitDriverConfiguration.class})
public @interface HtmlUnitTest {

    /**
     * replace MockMvcWebDriverAutoConfiguration with custom implementation to silence all JS errors
     */
    class HtmlUnitDriverConfiguration {

        @Bean
        public MockMvcHtmlUnitDriverBuilder mockMvcHtmlUnitDriverBuilder(MockMvc mockMvc, Environment environment) {
            LocalHostWebConnectionHtmlUnitDriver driver = new LocalHostWebConnectionHtmlUnitDriver(environment, BrowserVersion.CHROME);
            driver.getWebClient().getOptions().setThrowExceptionOnScriptError(false);
            driver.getWebClient().setJavaScriptErrorListener(new SilentJavaScriptErrorListener());
            return MockMvcHtmlUnitDriverBuilder.mockMvcSetup(mockMvc).withDelegate(driver);
        }
    }
}
