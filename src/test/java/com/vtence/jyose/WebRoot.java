package com.vtence.jyose;

import java.io.File;

public class WebRoot {

    private WebRoot() {}

    public static File locate() {
        return new File("src/main/webapp");
    }
}
