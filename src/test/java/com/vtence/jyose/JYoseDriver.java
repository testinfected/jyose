package com.vtence.jyose;

import com.objogate.wl.UnsynchronizedProber;
import com.objogate.wl.web.AsyncWebDriver;
import com.vtence.jyose.pages.HomePage;
import com.vtence.jyose.pages.MineSweeperPage;
import com.vtence.jyose.pages.PrimeFactorsPage;
import com.vtence.molecule.WebServer;

import java.io.File;
import java.io.IOException;

public class JYoseDriver {
    private final AsyncWebDriver browser;
    private final WebServer server;
    private final JYose yose;

    public JYoseDriver(int port, File webroot) {
        this.browser = new AsyncWebDriver(new UnsynchronizedProber(), Browsers.phantom());
        this.server = WebServer.create(port);
        this.yose = new JYose(webroot);
    }

    public void start() throws IOException {
        yose.start(server);
    }

    public void stop() throws IOException {
        server.stop();
        browser.quit();
    }

    public HomePage home() {
        browser.navigate().to(url("/"));
        return new HomePage(browser);
    }

    public PrimeFactorsPage primeFactors() {
        return home().primeFactors();
    }

    public MineSweeperPage minesweeper() {
        browser.navigate().to(url("/minesweeper"));
        return MineSweeperPage.inTestMode(browser);
    }

    private String url(String path) {
        return server.uri().resolve(path).toASCIIString();
    }
}