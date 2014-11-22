package com.vtence.jyose;

import com.objogate.wl.UnsynchronizedProber;
import com.objogate.wl.web.AsyncWebDriver;
import com.vtence.jyose.pages.HomePage;
import com.vtence.jyose.pages.PrimeFactorsPage;
import com.vtence.molecule.WebServer;
import org.openqa.selenium.WebDriver;

public class JYoseDriver {
    public static final int PORT = 9999;

    private final AsyncWebDriver browser;
    private final WebServer server;

    public JYoseDriver(WebDriver browser) {
        this.browser = new AsyncWebDriver(new UnsynchronizedProber(), browser);
        this.server = WebServer.create(PORT);
    }

    public HomePage home() {
        browser.navigate().to(url("/"));
        return new HomePage(browser);
    }

    public PrimeFactorsPage primeFactors() {
        return home().primeFactors();
    }

    private String url(String path) {
        return server.uri().resolve(path).toASCIIString();
    }
}