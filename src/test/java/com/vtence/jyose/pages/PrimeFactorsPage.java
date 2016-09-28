package com.vtence.jyose.pages;

import com.vtence.mario.BrowserDriver;
import com.vtence.mario.WebElementDriver;
import org.openqa.selenium.By;

import static java.util.stream.IntStream.range;
import static org.hamcrest.Matchers.equalTo;

public class PrimeFactorsPage {
    private final BrowserDriver browser;

    public PrimeFactorsPage(BrowserDriver browser) {
        this.browser = browser;
    }

    public void decompose(String number) {
        browser.element(By.id("number")).type(number);
        browser.element(By.id("go")).click();
    }

    public void showsSingleResult(String decomposition) {
        browser.element(By.id("result")).hasText(equalTo(decomposition));
    }

    public void showsResults(String... decompositions) {
        WebElementDriver results = browser.element(By.id("results"));
        range(0, decompositions.length).forEach(n -> nthItem(results, n + 1).hasText(equalTo(decompositions[n])));
    }

    private WebElementDriver nthItem(WebElementDriver results, int index) {
        return results.element(By.xpath("li[" + index + "]"));
    }

    public void showsLastDecomposition(String decomposition) {
        browser.element(By.id("last-decomposition")).hasText(equalTo(decomposition));
    }
}
