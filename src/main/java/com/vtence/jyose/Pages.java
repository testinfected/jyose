package com.vtence.jyose;

import com.vtence.molecule.templating.JMustacheRenderer;
import com.vtence.molecule.templating.Template;
import com.vtence.molecule.templating.Templates;

import java.io.File;

public class Pages {

    public final Templates views;

    public Pages(File webroot) {
        this.views = new Templates(
                new JMustacheRenderer().fromDir(new File(webroot, "views")).extension("html"));
    }

    public Template home() {
        return views.named("home");
    }

    public Template primes() {
        return views.named("primes");
    }

    public Template minesweeper() {
        return views.named("minesweeper");
    }
}
