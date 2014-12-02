package com.vtence.jyose.pages;

import com.objogate.wl.web.AsyncWebDriver;
import org.openqa.selenium.By;

public class HomePage {
    private final AsyncWebDriver browser;

    public HomePage(AsyncWebDriver browser) {
        this.browser = browser;
    }

    public PrimeFactorsPage primeFactors() {
        browser.element(By.id("prime-factors-decomposition-link")).click();
        return new PrimeFactorsPage(browser);
    }
}
