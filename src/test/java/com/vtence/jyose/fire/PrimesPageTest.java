package com.vtence.jyose.fire;

import com.vtence.jyose.ViewRenderer;
import com.vtence.jyose.WebRoot;
import org.junit.Test;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.IOException;

import static com.vtence.jyose.ViewRenderer.render;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testinfected.hamcrest.dom.DomMatchers.hasName;
import static org.testinfected.hamcrest.dom.DomMatchers.hasSelector;
import static org.testinfected.hamcrest.dom.DomMatchers.hasUniqueSelector;

public class PrimesPageTest {

    @Test
    public void includesATitle() throws Exception {
        Element view = renderPrimesPage().asDom();
        assertThat("view", view, hasUniqueSelector("#title"));
    }

    @Test
    public void promptsToEnterNumber() throws IOException, SAXException {
        Element view = renderPrimesPage().asDom();
        assertThat("view", view, hasUniqueSelector("#invitation"));
    }

    @Test @SuppressWarnings("unchecked")
    public void rendersDecompositionForm() throws IOException, SAXException {
        Element view = renderPrimesPage().asDom();
        assertThat("view", view, hasSelector("form",
                hasUniqueSelector("input#number", hasName("number")),
                hasUniqueSelector("button#go")));
    }

    private ViewRenderer renderPrimesPage() {
        return render("primes").from(WebRoot.views());
    }
}
