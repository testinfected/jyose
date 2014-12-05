package com.vtence.jyose.pages;

import com.objogate.wl.web.AsyncWebDriver;
import org.openqa.selenium.By;

import static java.util.stream.IntStream.rangeClosed;
import static org.hamcrest.Matchers.containsString;

public class MineSweeperPage {
    private final AsyncWebDriver browser;

    public MineSweeperPage(AsyncWebDriver browser) {
        this.browser = browser;
    }

    public void showsInTitle(String message) {
        browser.element(By.id("title")).assertText(containsString(message));
    }

    public void showsGridOfSize(int rows, int cols) {
        rangeClosed(1, rows).
                forEach(row -> rangeClosed(1, cols).
                        forEach(col -> browser.element(By.id(cell(row, col))).assertExists()));
    }

    private String cell(int row, int col) {
        return "cell-" + row + "x" + col;
    }
}
