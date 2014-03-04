package com.vtence.jyose;

import com.vtence.molecule.Application;
import com.vtence.molecule.Request;
import com.vtence.molecule.Response;
import com.vtence.molecule.Server;
import com.vtence.molecule.simple.SimpleServer;

import java.io.IOException;

public class JYose {

    public void start(Server server) throws IOException {
        server.run(new Application() {
            public void handle(Request request, Response response) throws Exception {
                response.body("Hello Yose");
            }
        });
    }

    public static void main(String[] args) throws IOException {
        JYose game = new JYose();
        Server server = new SimpleServer(port(args));
        game.start(server);
    }

    private static final int PORT = 0;

    private static int port(String[] args) {
        return Integer.parseInt(args[PORT]);
    }
}
