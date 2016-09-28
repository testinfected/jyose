package com.vtence.jyose.pages;

import com.vtence.mario.BrowserDriver;
import org.openqa.selenium.By;

public class HomePage {
    private final BrowserDriver browser;

    public HomePage(BrowserDriver browser) {
        this.browser = browser;
    }

    public PrimeFactorsPage primeFactors() {
        browser.element(By.id("prime-factors-decomposition-link")).click();
        return new PrimeFactorsPage(browser);
    }
}
