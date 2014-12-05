package com.vtence.jyose;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.openqa.selenium.phantomjs.PhantomJSDriverService.PHANTOMJS_CLI_ARGS;

public class Browsers {

    public static WebDriver phantom() {
        DesiredCapabilities config = new DesiredCapabilities();
        String[] args = new  String[] { "--webdriver-loglevel=NONE" };
        config.setCapability(PHANTOMJS_CLI_ARGS, args);
        Logger.getLogger("org.openqa.selenium.phantomjs.PhantomJSDriverService").setLevel(Level.OFF);
        return new PhantomJSDriver(config);
    }

    public static WebDriver firefox() {
        return new FirefoxDriver();
    }
}
