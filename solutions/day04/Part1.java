package day04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Part1 {
    private static final char[] TARGET_WORD = {'M', 'A', 'S'};
    private static final int[][] DIRECTIONS = {
        {0, 1}, {1, 0}, {1, 1}, {1, -1}, // right, down, down-right, down-left
        {0, -1}, {-1, 0}, {-1, -1}, {-1, 1} // left, up, up-left, up-right
    };

    public static void main(String[] args) throws IOException {
        BufferedReader f = new BufferedReader(new InputStreamReader(System.in));
        String line;
        List<String> lines = new ArrayList<>();
        while ((line = f.readLine()) != null) {
            lines.add(line);
        }

        char[][] grid = lines.stream()
            .map(String::toCharArray)
            .toArray(char[][]::new);

        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 'X') {
                    count += countMatches(grid, i, j);
                }
            }
        }
        System.out.println(count);
    }

    private static int countMatches(char[][] grid, int row, int col) {
        int count = 0;
        for (int[] dir : DIRECTIONS) {
            count += checkDirection(grid, row, col, dir[0], dir[1]);
        }
        return count;
    }

    private static int checkDirection(char[][] grid, int row, int col, int rowInc, int colInc) {
        for (int k = 1; k <= TARGET_WORD.length; k++) {
            int newRow = row + k * rowInc;
            int newCol = col + k * colInc;
            if (newRow < 0 || newRow >= grid.length || newCol < 0 || newCol >= grid[0].length || grid[newRow][newCol] != TARGET_WORD[k - 1]) {
                return 0;
            }
        }
        return 1;
    }

}
