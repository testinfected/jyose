package com.vtence.jyose;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vtence.jyose.fire.FireFighting;
import com.vtence.jyose.ping.Ping;
import com.vtence.jyose.primes.Primes;
import com.vtence.molecule.WebServer;
import com.vtence.molecule.middlewares.FileServer;
import com.vtence.molecule.middlewares.StaticAssets;
import com.vtence.molecule.routing.DynamicRoutes;

import java.io.File;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class JYose {

    private static final int PORT = 0;
    private static final int WEB_ROOT = 1;
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
        Pages pages = new Pages(webroot);
        server.add(staticAssets())
                .start(new DynamicRoutes() {{
                    get("/").to(new StaticPage(pages.home())::render);
                    get("/ping").to(new Ping(gson)::pong);
                    get("/primeFactors").to(new Primes(gson));
                    get("/primeFactors/ui").to(new StaticPage(pages.primes())::render);
                    get("/fire/geek").to(new FireFighting(gson));
                }});
    }

    private StaticAssets staticAssets() {
        return new StaticAssets(new FileServer(new File(webroot, "assets")),
                "/favicon.ico", "/images", "/css");
    }

    public static void main(String[] args) throws IOException {
        JYose game = new JYose(webroot(args), new GsonBuilder().setPrettyPrinting().create());
        WebServer server = WebServer.create("0.0.0.0", port(args));
        game.start(server);
    }

    private static int port(String[] args) {
        return parseInt(args[PORT]);
    }

    private static File webroot(String[] args) {
        return new File(args[WEB_ROOT]);
    }
}
