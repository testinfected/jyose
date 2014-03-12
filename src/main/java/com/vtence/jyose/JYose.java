package com.vtence.jyose;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vtence.molecule.Application;
import com.vtence.molecule.Request;
import com.vtence.molecule.Response;
import com.vtence.molecule.Server;
import com.vtence.molecule.middlewares.Router;
import com.vtence.molecule.mustache.JMustacheRenderer;
import com.vtence.molecule.routing.DynamicRoutes;
import com.vtence.molecule.simple.SimpleServer;
import com.vtence.molecule.templating.Templates;
import com.vtence.molecule.util.Charsets;
import com.vtence.molecule.util.MimeTypes;

import java.io.File;
import java.io.IOException;

public class JYose {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final File webroot;

    public JYose(File webroot) {
        this.webroot = webroot;
    }

    public static class Pong {
        public final boolean alive = true;
    }

    public void start(Server server) throws IOException {
        final Templates views = Templates.renderedWith(JMustacheRenderer.lookIn(new File(webroot,
                "views")).extension("html"));

        server.run(Router.draw(new DynamicRoutes() {{
            get("/ping").to(new Application() {
                public void handle(Request request, Response response) throws Exception {
                    response.contentType(MimeTypes.JSON);
                    response.body(gson.toJson(new Pong()));
                }
            });

            get("/").to(new Application() {
                public void handle(Request request, Response response) throws Exception {
                    response.contentType(MimeTypes.HTML);
                    views.html("home").render(response, null);
                }
            });
        }}));
    }

    public static void main(String[] args) throws IOException {
        JYose game = new JYose(webroot(args));
        SimpleServer server = new SimpleServer(port(args));
        server.defaultCharset(Charsets.UTF_8);
        game.start(server);
    }

    private static final int PORT = 0;
    private static final int WEB_ROOT = 1;

    private static int port(String[] args) {
        return Integer.parseInt(args[PORT]);
    }

    private static File webroot(String[] args) {
        return new File(args[WEB_ROOT]);
    }
}
