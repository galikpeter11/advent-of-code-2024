package day12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Part2 {
    private static final int[][] DIRECTIONS = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
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
                    Region region = new Region(GRID[i][j], 0, 0);
                    dfs(i, j, GRID[i][j], region);
                    resultList.add(region);
                }
            }
        }

        resultList.forEach(region -> System.out.println("A region of " + region.plant + " plants with price " + region.area + " * " + region.sides + " = " + region.area * region.sides));
        resultList.stream().map(region -> region.area * region.sides).reduce(Integer::sum).ifPresent(System.out::println);
    }

    private static boolean isGood(int row, int col, int[] dir) {
        int newRow = row + dir[0], newCol = col + dir[1];
        return isInside(newRow, newCol) && GRID[row][col] == GRID[newRow][newCol];
    }

    private static boolean isInside(int row, int col) {
        return row >= 0 && row < GRID.length && col >= 0 && col < GRID[0].length;
    }

    private static void dfs(int row, int col, char target, final Region region) {
        VISITED[row][col] = true;
        region.area++;

        for (int i = 0; i < 4; i++) {
            int[] dir1 = DIRECTIONS[i];
            int[] dir2 = DIRECTIONS[(i + 1) % 4];

            if (!isGood(row, col, dir1) && !isGood(row, col, dir2)) {
                region.sides++;
            }

            if (isGood(row, col, dir1) && isGood(row, col, dir2) && !isGood(row, col, new int[]{dir1[0] + dir2[0], dir1[1] + dir2[1]})) {
                region.sides++;
            }
        }


        for (int[] dir : DIRECTIONS) {
            int ni = row + dir[0], nj = col + dir[1];
            if (isInside(ni, nj) && !VISITED[ni][nj] && GRID[ni][nj] == target) {
                dfs(ni, nj, target, region);
            }
        }
    }

    private static final class Region {
        Character plant;
        Integer area, sides;

        public Region(Character plant, Integer area, Integer sides) {
            this.plant = plant;
            this.area = area;
            this.sides = sides;
        }
    }
}