package com.vtence.jyose.fire;

import org.junit.Test;

import static com.vtence.jyose.fire.Move.Down;
import static com.vtence.jyose.fire.Move.Left;
import static com.vtence.jyose.fire.Move.Right;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class SolverTest {

    @Test
    public void findsSolutionConsistingOfNoMove() throws Exception {
        Solver solver = new Solver();
        Path path = solver.solve(Pos.at(0, 0), Pos.at(0, 0)).get();
        assertThat("pos", path.pos(), equalTo(Pos.at(0, 0)));
        assertThat("moves", path.moves(), empty());
    }

    @Test
    public void findsSolutionConsistingOfSingleRightMove() throws Exception {
        Solver solver = new Solver();
        Path path = solver.solve(Pos.at(0, 0), Pos.at(0, 1)).get();
        assertThat("pos", path.pos(), equalTo(Pos.at(0, 1)));
        assertThat("moves", path.moves(), contains(Right));
    }

    @Test
    public void findsSolutionConsistingOfSingleDownMove() throws Exception {
        Solver solver = new Solver();
        Path path = solver.solve(Pos.at(0, 0), Pos.at(1, 0)).get();
        assertThat("pos", path.pos(), equalTo(Pos.at(1, 0)));
        assertThat("moves", path.moves(), contains(Down));
    }

    @Test
    public void findsSolutionConsistingOfSingleLeftMove() throws Exception {
        Solver solver = new Solver();
        Path path = solver.solve(Pos.at(0, 1), Pos.at(0, 0)).get();
        assertThat("pos", path.pos(), equalTo(Pos.at(0, 0)));
        assertThat("moves", path.moves(), contains(Left));
    }

    @Test
    public void findsSolutionConsistingOfTowConsecutiveRightMoves() throws Exception {
        Solver solver = new Solver();
        Path path = solver.solve(Pos.at(0, 0), Pos.at(0, 2)).get();
        assertThat("pos", path.pos(), equalTo(Pos.at(0, 2)));
        assertThat("moves", path.moves(), contains(Right, Right));
    }

    @Test
    public void solves00to33() throws Exception {
        Solver solver = new Solver();
        Path path = solver.solve(Pos.at(0, 0), Pos.at(3, 3)).get();
        assertThat("pos", path.pos(), equalTo(Pos.at(3, 3)));
        assertThat("moves", path.moves(), contains(Right, Right, Right, Down, Down, Down));
    }
}
