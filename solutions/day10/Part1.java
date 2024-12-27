package day10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Part1 {

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

        int result = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == '0') {
                    boolean[][] visited = new boolean[rows][cols];
                    int score = dfs(grid, i, j, '0', visited);
                    System.out.println("Score at " + i + " " + j + " is " + score);
                    result += score;
                }
            }
        }

        System.out.println(result);
    }

    private static int[][] directions = new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

    private static int dfs(char[][] grid, int i, int j, char target, boolean[][] visited) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != target || visited[i][j]) {
            return 0;
        }

        visited[i][j] = true;

        if (target == '9') {
            return 1;
        }

        int score = 0;
        for (int[] dir : directions) {
            score += dfs(grid, i + dir[0], j + dir[1], (char) (target + 1), visited);
        }

        return score;
    }
}