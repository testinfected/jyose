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
        assertThat(solution, hasDestination(0, 3));
        assertThat(solution, hasLength(3));
    }

    @Test
    public void findsPathToGoalByGoingDown() throws Exception {
        Terrain terrain = Terrain.parse(
                "S",
                ".",
                ".",
                "G");
        Path solution = findPathToGoal(terrain);
        assertThat(solution, hasDestination(3, 0));
        assertThat(solution, hasLength(3));
    }

    @Test
    public void findsPathToGoalByGoingLeft() throws Exception {
        Terrain terrain = Terrain.parse("G..S");
        Path solution = findPathToGoal(terrain);
        assertThat(solution, hasDestination(0, 0));
        assertThat(solution, hasLength(3));
    }

    @Test
    public void findsPathToGoalByGoingUp() throws Exception {
        Terrain terrain = Terrain.parse(
                "G",
                ".",
                ".",
                "S");
        Path solution = findPathToGoal(terrain);
        assertThat(solution, hasDestination(0, 0));
        assertThat(solution, hasLength(3));
    }

    @Test
    public void findsPathToOppositeOfTerrain() throws Exception {
        Terrain terrain = Terrain.parse(
                "S...",
                "....",
                "....",
                "...G");
        Path solution = findPathToGoal(terrain);
        assertThat(solution, hasDestination(3, 3));
        assertThat(solution, hasLength(6));
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
        assertThat(solution, hasDestination(4, 6));
        assertThat(solution, hasLength(22));
    }

    private Path findPathToGoal(Terrain terrain) {
        Vector start = terrain.find('S').get();
        Vector goal = terrain.find('G').get();
        Optional<Path> solution = Navigation.on(terrain).avoiding('-').findPath(start, goal);
        assertThat("no solution found", solution.isPresent());
        Path path = solution.get();
        path.steps().reduce(start, (pos, step) -> {
            Vector next = pos.plus(step);
            if (!terrain.contains(next) || terrain.at(next) == '-')
                throw new AssertionError("Illegal move: " + step + " to " + pos);
            return next;
        }, (left, right) -> right);
        return path;
    }

    private Matcher<? super Path> hasDestination(int row, int col) {
        return new FeatureMatcher<Path, Vector>(equalTo(Vector.pos(col, row)), "position", "position") {
            @Override
            protected Vector featureValueOf(Path actual) {
                return actual.end();
            }
        };
    }

    private Matcher<? super Path> hasLength(int count) {
        return new FeatureMatcher<Path, Integer>(equalTo(count), "total moves", "total") {
            @Override
            protected Integer featureValueOf(Path actual) {
                return actual.length();
            }
        };
    }
}