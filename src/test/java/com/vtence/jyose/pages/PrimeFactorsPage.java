package com.vtence.jyose.pages;

import com.objogate.wl.web.AsyncWebDriver;
import org.openqa.selenium.By;

import static org.hamcrest.Matchers.equalTo;

public class PrimeFactorsPage {
    private final AsyncWebDriver browser;

    public PrimeFactorsPage(AsyncWebDriver browser) {
        this.browser = browser;
    }

    public void decompose(String number) {
        browser.element(By.id("number")).type(number);
        browser.element(By.id("go")).click();
    }

    public void showsResult(String decomposition) {
        browser.element(By.id("result")).assertText(equalTo(decomposition));
    }
}
