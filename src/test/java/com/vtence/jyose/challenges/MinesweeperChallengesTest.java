package com.vtence.jyose.challenges;

import com.vtence.jyose.JYoseDriver;
import com.vtence.jyose.WebRoot;
import com.vtence.jyose.pages.MineSweeperPage;
import com.vtence.molecule.support.HttpRequest;
import com.vtence.molecule.support.HttpResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MinesweeperChallengesTest {

    static int PORT = 9999;

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
        minesweeper.showsGridOfSize(8, 8);
    }

    @Test
    public void passesDataInjectionChallenge() {
        MineSweeperPage minesweeper = yose.minesweeper();
        minesweeper.losesWhenRevealingCell(7, 3);
    }

    @Test
    public void passesSafeCellsChallenge() {
        MineSweeperPage minesweeper = yose.minesweeper();
        minesweeper.revealCell(3, 2);
        minesweeper.showsSafeCell(3, 2, "2");
    }

    @Test
    public void passesZeroBombAroundChallenge() {
        MineSweeperPage minesweeper = yose.minesweeper();
        minesweeper.revealCell(3, 6);
        minesweeper.showsSafeCell(3, 6, "");
    }

    @Test
    public void passesOpenFieldChallenge() {
        MineSweeperPage minesweeper = yose.minesweeper();
        minesweeper.revealCell(8, 8);
        minesweeper.showsSafeCell(1, 3, "");
    }

    @Test
    public void passesOpenRandomFieldChallenge() {
    }

    @Test
    public void passesSuspectModeChallenge() {
        MineSweeperPage minesweeper = yose.minesweeper();
        minesweeper.flagSuspectCell(3, 3);
        minesweeper.revealCell(3, 6);
        minesweeper.showsSuspectCell(3, 3);
        minesweeper.showsSafeCell(3, 6, "");
    }
}
