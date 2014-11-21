package com.vtence.jyose;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vtence.jyose.fire.FireFighting;
import com.vtence.jyose.ping.Ping;
import com.vtence.jyose.primes.Primes;
import com.vtence.molecule.WebServer;
import com.vtence.molecule.http.MimeTypes;
import com.vtence.molecule.middlewares.FileServer;
import com.vtence.molecule.middlewares.StaticAssets;
import com.vtence.molecule.routing.DynamicRoutes;
import com.vtence.molecule.templating.JMustacheRenderer;
import com.vtence.molecule.templating.Templates;

import java.io.File;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class JYose {

    private static final Object NO_CONTEXT = null;

    private final File webroot;
    private final Gson gson;

    public JYose(File webroot) {
        this(webroot, new Gson());
    }

    public JYose(File webroot, Gson gson) {
        this.webroot = webroot;
        this.gson = gson;
    }

    public void start(WebServer server) throws IOException {
        final Templates views = new Templates(
                new JMustacheRenderer().fromDir(new File(webroot, "views")).extension("html"));

        server.add(new StaticAssets(new FileServer(new File(webroot, "assets")), "/favicon.ico", "/images", "/css"))
              .start(new DynamicRoutes() {{

                  get("/").to((request, response) -> {
                      response.contentType(MimeTypes.HTML);
                      response.body(views.named("home").render(NO_CONTEXT));
                  });

                  get("/ping").to(new Ping(gson)::pong);

                  get("/primeFactors").to(new Primes(gson));

                  get("/primeFactors/ui").to((request, response) -> {
                      response.contentType(MimeTypes.HTML);
                      response.body(views.named("primes").render(NO_CONTEXT));
                  });

                  get("/fire/geek").to(new FireFighting(gson));
              }});
    }

    public static void main(String[] args) throws IOException {
        JYose game = new JYose(webroot(args), new GsonBuilder().setPrettyPrinting().create());
        WebServer server = WebServer.create("0.0.0.0", port(args));
        game.start(server);
    }

    private static final int PORT = 0;
    private static final int WEB_ROOT = 1;

    private static int port(String[] args) {
        return parseInt(args[PORT]);
    }

    private static File webroot(String[] args) {
        return new File(args[WEB_ROOT]);
    }
}
