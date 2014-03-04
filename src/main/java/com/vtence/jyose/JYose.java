package com.vtence.jyose;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vtence.molecule.Application;
import com.vtence.molecule.Request;
import com.vtence.molecule.Response;
import com.vtence.molecule.Server;
import com.vtence.molecule.middlewares.Router;
import com.vtence.molecule.routing.DynamicRoutes;
import com.vtence.molecule.simple.SimpleServer;

import java.io.IOException;

public class JYose {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static class Pong {
        public final boolean alive = true;
    }

    public void start(Server server) throws IOException {
        server.run(Router.draw(new DynamicRoutes() {{
            get("/ping").to(new Application() {
                public void handle(Request request, Response response) throws Exception {
                    response.contentType("application/json");
                    response.body(gson.toJson(new Pong()));
                }
            });

            get("/").to(new Application() {
                public void handle(Request request, Response response) throws Exception {
                    response.body("Hello Yose");
                }
            });
        }}));
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
