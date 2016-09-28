package com.vtence.jyose.pages;

import com.vtence.mario.BrowserDriver;
import com.vtence.mario.WebElementDriver;
import org.openqa.selenium.By;

import static java.util.stream.IntStream.rangeClosed;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class MineSweeperPage {
    private final BrowserDriver browser;

    public static MineSweeperPage inTestMode(BrowserDriver browser) {
        MineSweeperPage page = new MineSweeperPage(browser);
        page.enterTestMode();
        return page;
    }

    public MineSweeperPage(BrowserDriver browser) {
        this.browser = browser;
    }

    public void showsInTitle(String message) {
        browser.element(By.id("title")).hasText(containsString(message));
    }

    public void showsGridOfSize(int rows, int cols) {
        rangeClosed(1, rows).
                forEach(row -> rangeClosed(1, cols).
                        forEach(col -> browser.element(By.id(cell(row, col))).exists()));
    }

    private String cell(int row, int col) {
        return "cell-" + row + "x" + col;
    }

    public void losesWhenRevealingCell(int row, int col) {
        browser.element(By.id(cell(row, col))).click();
        browser.element(By.cssSelector(bombCell(row, col))).exists();
    }

    public void revealCell(int row, int col) {
        browser.element(By.id(cell(row, col))).click();
    }

    public void showsSafeCell(int row, int col, String content) {
        browser.element(By.cssSelector(safeCell(row, col))).exists();
        browser.element(By.cssSelector(safeCell(row, col))).hasText(equalTo(content));
    }

    public void flagSuspectCell(int row, int col) {
        toggleSuspectMode();
        revealCell(row, col);
        toggleSuspectMode();
    }

    private void toggleSuspectMode() {
        WebElementDriver toggle = browser.element(By.cssSelector("input#suspect-mode[type=checkbox]"));
        toggle.click();
    }

    public void showsSuspectCell(int row, int col) {
        browser.element(By.cssSelector(suspectCell(row, col))).exists();
        browser.element(By.cssSelector(suspectCell(row, col))).hasText(equalTo(""));
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
