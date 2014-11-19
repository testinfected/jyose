package com.vtence.jyose.fire;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class NavigationTest {

    @Test
    public void findsPathToGoalByGoingRight() throws Exception {
        Terrain terrain = Terrain.parse("S..G");
        Path solution = findPathToGoal(terrain);
        assertThat(solution, hasLocation(0, 3));
        assertThat(solution, hasTotalMoves(3));
    }

    @Test
    public void findsPathToGoalByGoingDown() throws Exception {
        Terrain terrain = Terrain.parse(
                "S",
                ".",
                ".",
                "G");
        Path solution = findPathToGoal(terrain);
        assertThat(solution, hasLocation(3, 0));
        assertThat(solution, hasTotalMoves(3));
    }

    @Test
    public void findsPathToGoalByGoingLeft() throws Exception {
        Terrain terrain = Terrain.parse("G..S");
        Path solution = findPathToGoal(terrain);
        assertThat(solution, hasLocation(0, 0));
        assertThat(solution, hasTotalMoves(3));
    }

    @Test
    public void findsPathToGoalByGoingUp() throws Exception {
        Terrain terrain = Terrain.parse(
                "G",
                ".",
                ".",
                "S");
        Path solution = findPathToGoal(terrain);
        assertThat(solution, hasLocation(0, 0));
        assertThat(solution, hasTotalMoves(3));
    }

    @Test
    public void findsPathToOppositeOfTerrain() throws Exception {
        Terrain terrain = Terrain.parse(
                "S...",
                "....",
                "....",
                "...G");
        Path solution = findPathToGoal(terrain);
        assertThat(solution, hasLocation(3, 3));
        assertThat(solution, hasTotalMoves(6));
    }

    @Test
    public void avoidsTerrainObstacles() throws Exception {
        Terrain terrain = Terrain.parse(
                "S.-....",
                ".-..--.",
                ".-.-...",
                ".-.-.--",
                "...-..G");
        Path solution = findPathToGoal(terrain);
        assertThat(solution, hasLocation(4, 6));
        assertThat(solution, hasTotalMoves(22));
    }

    private Path findPathToGoal(Terrain terrain) {
        Pos start = terrain.find('S').get();
        Pos goal = terrain.find('G').get();
        Optional<Path> solution = Navigation.on(terrain).avoiding('-').findPath(start, goal);
        assertThat("no solution found", solution.isPresent());
        Path path = solution.get();
        path.moves().reduce(start, (pos, move) -> {
            Pos next = move.from(pos);
            if (!terrain.contains(next) || terrain.at(next) == '-')
                throw new AssertionError("Illegal move: " + move + " to " + pos);
            return next;
        }, (left, right) -> right);
        return path;
    }

    private Matcher<? super Path> hasLocation(int row, int col) {
        return new FeatureMatcher<Path, Pos>(equalTo(Pos.at(row, col)), "position", "position") {
            @Override
            protected Pos featureValueOf(Path actual) {
                return actual.pos();
            }
        };
    }

    private Matcher<? super Path> hasTotalMoves(long count) {
        return new FeatureMatcher<Path, Long>(equalTo(count), "total moves", "total") {
            @Override
            protected Long featureValueOf(Path actual) {
                return actual.moves().count();
            }
        };
    }
}