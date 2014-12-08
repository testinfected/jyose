package com.vtence.jyose;

import com.vtence.molecule.templating.JMustacheRenderer;
import com.vtence.molecule.templating.Template;
import com.vtence.molecule.templating.Templates;

import java.io.File;

public class Pages {

    public final Templates templates;

    public Pages(File webroot) {
        this.templates = new Templates(
                new JMustacheRenderer().fromDir(new File(webroot, "views")).extension("html"));
    }

    public Template home() {
        return templates.named("home");
    }

    public Template primes() {
        return templates.named("primes");
    }

    public Template minesweeper() {
        return templates.named("minesweeper");
    }
}
