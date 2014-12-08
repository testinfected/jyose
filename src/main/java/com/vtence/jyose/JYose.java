package com.vtence.jyose;

import com.google.gson.Gson;
import com.vtence.jyose.fire.FireFighting;
import com.vtence.jyose.ping.Ping;
import com.vtence.jyose.primes.Primes;
import com.vtence.molecule.WebServer;
import com.vtence.molecule.middlewares.CookieSessionTracker;
import com.vtence.molecule.middlewares.Failsafe;
import com.vtence.molecule.middlewares.FileServer;
import com.vtence.molecule.middlewares.StaticAssets;
import com.vtence.molecule.routing.DynamicRoutes;
import com.vtence.molecule.session.SessionPool;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import static com.vtence.molecule.http.HttpMethod.GET;
import static com.vtence.molecule.http.HttpMethod.POST;
import static java.lang.Integer.parseInt;

public class JYose {

    private static final int PORT = 0;
    private static final int WEB_ROOT = 1;
    private final File webroot;
    private final Gson gson;
    private final PrintStream out;

    public JYose(File webroot) {
        this.webroot = webroot;
        this.gson = new Gson();
        this.out = System.out;
    }

    public void start(WebServer server) throws IOException {
        Pages pages = new Pages(webroot);
        server.failureReporter(this::errorOccurred)
                .add(new Failsafe())
                .add(staticAssets())
                .add(new CookieSessionTracker(new SessionPool()))
                .start(new DynamicRoutes() {{
                    Primes primes = new Primes(gson);
                    get("/").to(new StaticPage(pages.home())::render);
                    get("/ping").to(new Ping(gson)::pong);
                    map("/primeFactors").via(GET, POST).to(primes::list);
                    get("/primeFactors/ui").to(new StaticPage(pages.primes())::render);
                    get("/primeFactors/last").to(primes::last);
                    get("/fire/geek").to(new FireFighting(gson));
                    get("/minesweeper").to(new StaticPage(pages.minesweeper())::render);
                }});
    }

    private StaticAssets staticAssets() {
        return new StaticAssets(new FileServer(new File(webroot, "assets")),
                "/favicon.ico", "/images", "/css", "/js");
    }

    public void errorOccurred(Throwable error) {
        out.println("[ERROR] " + error.getMessage());
        error.printStackTrace(out);
    }

    public static void main(String[] args) throws IOException {
        JYose game = new JYose(webroot(args));
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
