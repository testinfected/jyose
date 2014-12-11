package com.vtence.jyose.pages;

import com.objogate.wl.web.AsyncElementDriver;
import com.objogate.wl.web.AsyncWebDriver;
import org.openqa.selenium.By;

import static java.util.stream.IntStream.rangeClosed;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class MineSweeperPage {
    private final AsyncWebDriver browser;

    public static MineSweeperPage inTestMode(AsyncWebDriver browser) {
        MineSweeperPage page = new MineSweeperPage(browser);
        page.enterTestMode();
        return page;
    }

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

    public void losesWhenRevealingCell(int row, int col) {
        browser.element(By.id(cell(row, col))).click();
        browser.element(By.cssSelector(bombCell(row, col))).assertExists();
    }

    public void revealCell(int row, int col) {
        browser.element(By.id(cell(row, col))).click();
    }

    public void showsSafeCell(int row, int col, String content) {
        browser.element(By.cssSelector(safeCell(row, col))).assertExists();
        browser.element(By.cssSelector(safeCell(row, col))).assertText(equalTo(content));
    }

    public void flagSuspectCell(int row, int col) {
        toggleSuspectMode();
        revealCell(row, col);
        toggleSuspectMode();
    }

    private void toggleSuspectMode() {
        AsyncElementDriver toggle = browser.element(By.cssSelector("input#suspect-mode[type=checkbox]"));
        toggle.click();
    }

    public void showsSuspectCell(int row, int col) {
        browser.element(By.cssSelector(suspectCell(row, col))).assertExists();
        browser.element(By.cssSelector(suspectCell(row, col))).assertText(equalTo(""));
    }

    private String bombCell(int row, int col) {
        return "#" + cell(row, col) + ".lost";
    }

    private String safeCell(int row, int col) {
        return "#" + cell(row, col) + ".safe";
    }

    private String suspectCell(int row, int col) {
        return "#" + cell(row, col) + ".suspect";
    }

    public void enterTestMode() {
        browser.element(By.id("test-mode")).click();
    }
}
