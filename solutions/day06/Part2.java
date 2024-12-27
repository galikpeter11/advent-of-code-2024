package day06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

public class Part2 {

    public static void main(String[] args) throws IOException {
        BufferedReader f = new BufferedReader(new InputStreamReader(System.in));
        String line;
        List<String> lines = new ArrayList<>();
        while ((line = f.readLine()) != null) {
            lines.add(line);
        }
        char[][] grid = lines.stream().map(String::toCharArray).toArray(char[][]::new);
        System.out.println(walkManyTimes(grid));
    }

    static int linePositionInFrontOfGuard(Guard guard) {
        switch (guard.dir) {
            case up:
                return guard.line - 1;
            case down:
                return guard.line + 1;
            case left:
                return guard.line;
            case right:
                return guard.line;
            default:
                assert 0 == 1;
                return 0;
        }
    }

    static int colPositionInFrontOfGuard(Guard guard) {
        switch (guard.dir) {
            case up:
                return guard.col;
            case down:
                return guard.col;
            case left:
                return guard.col - 1;
            case right:
                return guard.col + 1;
            default:
                assert 0 == 1;
                return 0;
        }
    }

    static char charAt(char[][] area, int line, int col) {
        if (line < 0 || line >= area.length || col < 0 || col >= area[0].length) {
            return ' ';
        }

        return area[line][col];
    }

    static char whatIsInFrontOfGuard(char[][] area, Guard guard, int additionalBlockerLine, int addtionalBlockerCol) {
        int newLine = linePositionInFrontOfGuard(guard);
        int newCol = colPositionInFrontOfGuard(guard);

        if (newLine == additionalBlockerLine && newCol == addtionalBlockerCol) {
            return '#';
        }

        return charAt(area, newLine, newCol);
    }

    static Guard whereIsGuard(char[][] area) {
        int lineNum = 0, col = 0;
        for (char[] line : area) {
            col = 0;
            for (char symbol : line) {
                if (symbol == '^') {
                    return new Guard(lineNum, col, Direction.up);
                }
                col++;
            }
            lineNum++;
        }
        return null;
    }

    static void turnGuard90DegreesRight(Guard guard) {
        switch (guard.dir) {
            case up:
                guard.dir = Direction.right;
                break;
            case right:
                guard.dir = Direction.down;
                break;
            case down:
                guard.dir = Direction.left;
                break;
            case left:
                guard.dir = Direction.up;
        }
    }

    static void moveOneStep(char[][] area, int additionalBlockerLine, int additionalBlockerCol, Guard guard, java.util.LinkedHashSet<Guard> visitedPositions, State state) {
        char charInFront = whatIsInFrontOfGuard(area, guard, additionalBlockerLine, additionalBlockerCol);

        switch (charInFront) {
            case ' ':
                state.leaving = true;
                return;
            case '#':
                turnGuard90DegreesRight(guard);
                moveOneStep(area, additionalBlockerLine, additionalBlockerCol, guard, visitedPositions, state);
                if (state.leaving == true || state.loopDetected == true) {
                    return;
                }
                break;

            default:
                int newLine = linePositionInFrontOfGuard(guard);
                int newCol = colPositionInFrontOfGuard(guard);
                guard.line = newLine;
                guard.col = newCol;

                rememberDirection(guard, visitedPositions, state);
                if (state.loopDetected) {
                    return;
                }
        }
    }

    static void rememberDirection(Guard guard, LinkedHashSet<Guard> visitedPositions, State state) {
        if (visitedPositions.contains(guard)) {
            state.loopDetected = true;
            return;
        }
        visitedPositions.add(guard.clone());

    }

    static LinkedHashSet<Guard> firstWalk(Guard start, char[][] area) {
        Guard guard = start.clone();

        LinkedHashSet<Guard> visitedPositions = new LinkedHashSet<>();
        visitedPositions.add(start);

        State state = new State();

        while (true) {
            moveOneStep(area, -1, -1, guard, visitedPositions, state);
            if (state.leaving == true) {
                return visitedPositions;
            }
        }
    }

    static boolean furtherWalk(char[][] area, int additionalBlockerLine, int additionalBlockerCol, Guard currentPosition, LinkedHashSet<Guard> previousVisitedPositions) {
        Guard guard = currentPosition.clone();
        @SuppressWarnings("unchecked") LinkedHashSet<Guard> visitedPositions = (LinkedHashSet<Guard>) previousVisitedPositions.clone();

        State state = new State();

        turnGuard90DegreesRight(guard);

        while (true) {
            moveOneStep(area, additionalBlockerLine, additionalBlockerCol, guard, visitedPositions, state);
            if (state.leaving) {
                return false; // ending but no loop detected
            }
            if (state.loopDetected) {
                return true;
            }
            // otherwise continue
        }
    }

    static boolean containsPositionWithoutDirection(LinkedHashSet<Guard> visitedPositionsAndDirections, int line, int col) {
        for (Guard positionAndDirection : visitedPositionsAndDirections) {
            if (positionAndDirection.line == line && positionAndDirection.col == col) {
                return true;
            }
        }
        return false;
    }

    static int walkManyTimes(char[][] area) {
        Guard start = whereIsGuard(area);

        LinkedHashSet<Guard> visitedPositionsAndDirections = firstWalk(start, area);

        LinkedHashSet<Guard> partialVisitedPositions = new LinkedHashSet<>();
        Guard lastPosition = start;
        int numBlocks = 0;

        int index = 0;
        for (Guard positionAndDirection : visitedPositionsAndDirections) {
            if (index == 0) {
                partialVisitedPositions.add(positionAndDirection);
                lastPosition = positionAndDirection;
                index++;
                continue;
            }
            // have we already been at this location during the first walk? Don't put the same obstacle twice
            boolean contains = containsPositionWithoutDirection(partialVisitedPositions, positionAndDirection.line, positionAndDirection.col);
            if (!contains) {
                if (furtherWalk(area, positionAndDirection.line, positionAndDirection.col, lastPosition, partialVisitedPositions)) {
                    numBlocks++;
                }
            }
            partialVisitedPositions.add(positionAndDirection);
            lastPosition = positionAndDirection;
            index++;
        }
        return numBlocks;
    }

    private enum Direction {
        up, down, left, right
    }

    private static class Guard {
        int line;

        int col;

        Direction dir;

        public Guard(int line, int col, Direction dir) {
            this.line = line;
            this.col = col;
            this.dir = dir;
        }

        @Override
        public Guard clone() {
            return new Guard(line, col, dir);
        }

        @Override
        public String toString() {
            return line + " " + col + " " + dir;
        }

        @Override
        public int hashCode() {
            return Objects.hash(col, dir, line);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            Guard other = (Guard) obj;
            return col == other.col && dir == other.dir && line == other.line;
        }

    }

    static class State {
        boolean leaving;

        boolean loopDetected;
    }

}
