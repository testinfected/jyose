package com.vtence.jyose.pages;

import com.objogate.wl.web.AsyncElementDriver;
import com.objogate.wl.web.AsyncWebDriver;
import org.openqa.selenium.By;

import static java.util.stream.IntStream.range;
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

    public void showsSingleResult(String decomposition) {
        browser.element(By.id("result")).assertText(equalTo(decomposition));
    }

    public void showsResults(String... decompositions) {
        AsyncElementDriver results = browser.element(By.id("results"));
        range(0, decompositions.length).forEach(n -> nthItem(results, n + 1).assertText(equalTo(decompositions[n])));
    }

    private AsyncElementDriver nthItem(AsyncElementDriver results, int index) {
        return results.element(By.xpath("li[" + index + "]"));
    }
}
