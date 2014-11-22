package org.openqa.selenium.browserlaunchers;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Proxy;

// To make phantom compatible with selenium 2.44+
public class Proxies {
    public static Proxy extractProxy(Capabilities capabilities) {
        return Proxy.extractFrom(capabilities);
    }
}