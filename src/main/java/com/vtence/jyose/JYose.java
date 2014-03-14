package com.vtence.jyose;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vtence.molecule.Application;
import com.vtence.molecule.Request;
import com.vtence.molecule.Response;
import com.vtence.molecule.Server;
import com.vtence.molecule.middlewares.FileServer;
import com.vtence.molecule.middlewares.MiddlewareStack;
import com.vtence.molecule.middlewares.Router;
import com.vtence.molecule.middlewares.StaticAssets;
import com.vtence.molecule.mustache.JMustacheRenderer;
import com.vtence.molecule.routing.DynamicRoutes;
import com.vtence.molecule.simple.SimpleServer;
import com.vtence.molecule.templating.Templates;
import com.vtence.molecule.util.Charsets;
import com.vtence.molecule.util.MimeTypes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

    public static File viewsIn(File webroot) { return new File(webroot, "views"); }
    public static File assetsIn(File webroot) { return new File(webroot, "assets"); }

    public void start(Server server) throws IOException {
        final Templates views = Templates.renderedWith(
                JMustacheRenderer.lookIn(viewsIn(webroot)).extension("html"));

        server.run(new MiddlewareStack() {{

            use(new StaticAssets(new FileServer(assetsIn(webroot)), "/favicon.ico",
                    "/images", "/css"));

            run(Router.draw(new DynamicRoutes() {{
                get("/ping").to(new Application() {
                    public void handle(Request request, Response response) throws Exception {
                        response.contentType(MimeTypes.JSON);
                        response.body(gson.toJson(new Pong()));
                    }

                    class Pong {
                        public final boolean alive = true;
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
                        List<Object> answers = new ArrayList<>();
                        for (String number : request.parameters("number")) {
                            answers.add(decomposeToPrimeFactors(number));
                        }

                        respondWith(response, answers);
                    }

                    private void respondWith(Response response, List<Object> answers) throws IOException {
                        response.contentType(MimeTypes.JSON);
                        if (answers.size() == 1) {
                            response.body(gson.toJson(answers.get(0)));
                        } else {
                            response.body(gson.toJson(answers));
                        }
                    }

                    private Object decomposeToPrimeFactors(String number) {
                        if (!isAnInteger(number)) {
                            return new NotANumber(number);
                        } else if (isTooBig(toInt(number))) {
                            return new NumberTooBig(toInt(number));
                        } else {
                            return new PrimeFactorsDecomposition(toInt(number));
                        }
                    }

                    private int toInt(String number) {
                        return Integer.parseInt(number);
                    }

                    private boolean isAnInteger(String candidate) {
                        try {
                            toInt(candidate);
                            return true;
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    }

                    private boolean isTooBig(int number) {
                        return number > 1000000;
                    }

                    class PrimeFactorsDecomposition {
                        private final int number;
                        private final List<Integer> decomposition;

                        public PrimeFactorsDecomposition(int number) {
                            this.number = number;
                            this.decomposition = PrimeFactors.of(number);
                        }
                    }

                    class NumberTooBig {
                        private final int number;
                        private final String error = "too big number (>1e6)";

                        public NumberTooBig(int number) {
                            this.number = number;
                        }
                    }

                    class NotANumber {
                        private final String number;
                        private final String error = "not a number";

                        public NotANumber(String number) {
                            this.number = number;
                        }
                    }
                });
            }}));
        }});
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
