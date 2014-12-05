package com.vtence.jyose.challenges;

import com.vtence.jyose.JYoseDriver;
import com.vtence.jyose.WebRoot;
import com.vtence.jyose.pages.MineSweeperPage;
import com.vtence.molecule.support.HttpRequest;
import com.vtence.molecule.support.HttpResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class MinesweeperChallengesTest {

    static int PORT = 9999;

    HttpRequest request = new HttpRequest(PORT);
    HttpResponse response;

    JYoseDriver yose = new JYoseDriver(PORT, WebRoot.locate());

    @Before
    public void startServer() throws Exception {
        yose.start();
    }

    @After
    public void stopServer() throws Exception {
        yose.stop();
    }

    @Test
    public void passesBoardChallenge() {
        MineSweeperPage minesweeper = yose.minesweeper();
        minesweeper.showsInTitle("Minesweeper");
        minesweeper.showsGridOfSize(2, 2);
    }
}