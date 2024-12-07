package day04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Part2 {
    public static void main(String[] args) throws IOException {

        BufferedReader f = new BufferedReader(new InputStreamReader(System.in));
        String line;
        List<String> lines = new ArrayList<>();
        while ((line = f.readLine()) != null) {
            lines.add(line);
        }

        char[][] grid = lines.stream().map(String::toCharArray).toArray(char[][]::new);

        int count = 0;
        for (int i = 1; i < grid.length - 1; i++) {
            for (int j = 1; j < grid[i].length - 1; j++) {
                if (grid[i][j] == 'A') {
                    count += checkXMASPattern(grid, i, j);
                }
            }
        }
        System.out.println(count);
    }

    private static int checkXMASPattern(char[][] grid, int row, int col) {
        int result = 0;

        if (isXMAS(grid, row, col, 1, 1) && isXMAS(grid, row, col, -1, 1)) {
            result++;
        }

        return result;
    }

    private static boolean isXMAS(char[][] grid, int row, int col, int rowInc, int colInc) {
        char first = grid[row + rowInc][col + colInc];
        char second = grid[row - rowInc][col - colInc];
        return (first == 'M' && second == 'S') || (first == 'S' && second == 'M');
    }

}
