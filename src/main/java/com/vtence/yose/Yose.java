package com.vtence.yose;

import com.vtence.molecule.Application;
import com.vtence.molecule.Request;
import com.vtence.molecule.Response;
import com.vtence.molecule.simple.SimpleServer;

import java.io.IOException;

public class Yose {

    private static final int PORT = 0;

    public static void main(String[] args) throws IOException {
        SimpleServer server = new SimpleServer(port(args));

        server.run(new Application() {
            public void handle(Request request, Response response) throws Exception {
                response.body("Hello Yose");
            }
        });
    }

    private static int port(String[] args) {
        return Integer.parseInt(args[PORT]);
    }
}
