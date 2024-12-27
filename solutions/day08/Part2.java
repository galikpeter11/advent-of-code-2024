package day08;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Part2 {
    public static void main(String[] args) throws IOException {
        BufferedReader f = new BufferedReader(new InputStreamReader(System.in));
        String line;
        List<String> lines = new ArrayList<>();
        while ((line = f.readLine()) != null) {
            lines.add(line);
        }

        char[][] grid = lines.stream().map(String::toCharArray).toArray(char[][]::new);


        int rows = grid.length;
        int cols = grid[0].length;

        Set<Character> found = new HashSet<>();
        List<Set<Coord>> groups = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] != '.') {
                    if (found.contains(grid[i][j])) {
                        continue;
                    }
                    found.add(grid[i][j]);
                    groups.add(find(grid, i, j));
                }
            }
        }

        for (int i = 0; i < groups.size(); i++) {
            for (int j = 0; j < groups.get(i).size() - 1; j++) {
                for (int k = j + 1; k < groups.get(i).size(); k++) {
                    Coord a = new ArrayList<>(groups.get(i)).get(j);
                    Coord b = new ArrayList<>(groups.get(i)).get(k);
                    int dx = a.x - b.x;
                    int dy = a.y - b.y;

                    putAntiNodes(grid, a, dx, dy);
                    putAntiNodes(grid, a, -dx, -dy);
                }
            }
        }

        // Count the number of '#' in the grid
        int count = 0;
        for (int i = 0; i < rows; i++) {
            System.out.println(grid[i]);
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == '#') {
                    count++;
                }
            }
        }
        System.out.println(count);
    }

    private static void putAntiNodes(char[][] grid, Coord a, int dx, int dy) {
        int rows = grid.length;
        int cols = grid[0].length;

        grid[a.x][a.y] = '#';
        while (a.x + dx >= 0 && a.x + dx < rows && a.y + dy >= 0 && a.y + dy < cols) {
            grid[a.x + dx][a.y + dy] = '#';
            a = new Coord(a.x + dx, a.y + dy);
        }
    }

    private static Set<Coord> find(char[][] grid, int i, int j) {
        Set<Coord> result = new HashSet<>();
        int rows = grid.length;
        int cols = grid[0].length;

        char c = grid[i][j];
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                if (grid[x][y] == c) {
                    result.add(new Coord(x, y));
                }
            }
        }

        return result;
    }

    private static class Coord {
        int x, y;

        Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
