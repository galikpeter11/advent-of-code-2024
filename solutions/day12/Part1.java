package day12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Part1 {
    private static final int[][] DIRECTIONS = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private static final Set<Region> resultList = new LinkedHashSet<>();
    private static char[][] GRID;
    private static boolean[][] VISITED;

    public static void main(String[] args) throws IOException {
        BufferedReader f = new BufferedReader(new InputStreamReader(System.in));
        String line;
        List<String> lines = new ArrayList<>();
        while ((line = f.readLine()) != null) {
            lines.add(line);
        }

        GRID = lines.stream().map(String::toCharArray).toArray(char[][]::new);
        int W = GRID[0].length, H = GRID.length;
        VISITED = new boolean[H][W];

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (!VISITED[i][j]) {
                    dfs(i, j, GRID[i][j], new Region(GRID[i][j], 1, 0));
                }
            }
        }

        //resultList.forEach(region -> System.out.println("A region of " + region.plant + " plants with price " + region.area + " * " + region.perimeter + " = " + region.area * region.perimeter));
        resultList.stream().map(region -> region.area * region.perimeter).reduce(Integer::sum).ifPresent(System.out::println);
    }

    private static void dfs(int i, int j, char target, final Region region) {
        VISITED[i][j] = true;

        // Check if the current cell is on the border
        for (int[] dir : DIRECTIONS) {
            int ni = i + dir[0], nj = j + dir[1];
            if (!(ni >= 0 && ni < GRID.length && nj >= 0 && nj < GRID[0].length) || GRID[ni][nj] != target) {
                region.perimeter++;
            }
        }

        for (int[] dir : DIRECTIONS) {
            int ni = i + dir[0], nj = j + dir[1];
            if (ni >= 0 && ni < GRID.length && nj >= 0 && nj < GRID[0].length && !VISITED[ni][nj] && GRID[ni][nj] == target) {
                dfs(ni, nj, target, region);
                region.area++;

            }
            resultList.add(region);
        }
    }


    private static final class Region {
        Character plant;
        Integer area, perimeter;

        public Region(Character plant, Integer area, Integer perimeter) {
            this.plant = plant;
            this.area = area;
            this.perimeter = perimeter;
        }
    }
}