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
import java.util.List;

public class JYose {

    private final File webroot;
    private final Gson gson;

    public JYose(File webroot) {
        this(webroot, new Gson());
    }

    public JYose(File webroot, Gson gson) {
        this.webroot = webroot;
        this.gson = gson;
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

            get("/primeFactors").to(new Application() {
                public void handle(Request request, Response response) throws Exception {
                    int number = Integer.parseInt(request.parameter("number"));
                    response.contentType(MimeTypes.JSON);
                    response.body(gson.toJson(new PrimeFactorsDecomposition(number)));
                }
            });
        }}));
    }

    public static class Pong {
        public final boolean alive = true;
    }

    public static class PrimeFactorsDecomposition {
        private final int number;
        private final List<Integer> decomposition;

        public PrimeFactorsDecomposition(int number) {
            this.number = number;
            this.decomposition = PrimeFactors.of(number);
        }
    }

    public static void main(String[] args) throws IOException {
        JYose game = new JYose(webroot(args), new GsonBuilder().setPrettyPrinting().create());
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
