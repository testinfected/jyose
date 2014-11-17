package com.vtence.jyose.fire;

import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SolverTest {

    Terrain terrain;

    @Test
    public void findsSolutionWhenNoMoveIsRequired() throws Exception {
        Solver solver = solverFor(Terrain.parse(""));
        assertFindsSolution(solver, Pos.at(0, 0), Pos.at(0, 0));
    }

    @Test
    public void findsSolutionByGoingRight() throws Exception {
        Solver solver = solverFor(Terrain.parse("...."));
        assertFindsSolution(solver, Pos.at(0, 0), Pos.at(0, 3));
    }

    @Test
    public void findsSolutionByGoingDown() throws Exception {
        Solver solver = solverFor(Terrain.parse(".", ".", ".", "."));
        assertFindsSolution(solver, Pos.at(0, 0), Pos.at(3, 0));
    }

    @Test
    public void findsSolutionByGoingLeft() throws Exception {
        Solver solver = solverFor(Terrain.parse("...."));
        assertFindsSolution(solver, Pos.at(0, 3), Pos.at(0, 0));
    }

    @Test
    public void findsSolutionAtOppositeOfTerrain() throws Exception {
        Solver solver = solverFor(Terrain.parse("....", "....", "....", "...."));
        assertFindsSolution(solver, Pos.at(0, 0), Pos.at(3, 3));
    }

    private Solver solverFor(Terrain terrain) {
        this.terrain = terrain;
        return new Solver();
    }

    private void assertFindsSolution(Solver solver, Pos start, Pos goal) {
        Optional<Path> solution = solver.solve(start, goal);
        assertThat("no solution found", solution.isPresent());
        Pos end = solution.get().moves().reduce(start, (pos, move) -> {
            Pos next = move.from(pos);
            if (!terrain.legal(next)) throw new AssertionError("Illegal move");
            return next;
        }, (left, right) -> right);
        assertThat("pos", end, equalTo(goal));
    }
}
