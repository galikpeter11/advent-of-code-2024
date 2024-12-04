package com.pgalik.AdventOfCode.day04;

import java.io.IOException;
import java.util.List;

import com.pgalik.AdventOfCode.FileService;

import lombok.RequiredArgsConstructor;

@org.springframework.stereotype.Service("day04Service")
@RequiredArgsConstructor
public class Service {
    public static final String INPUT_FILE = "inputs/day04.txt";
    private final FileService fileService;
    private static final char[] TARGET_WORD = {'M', 'A', 'S'};
    private static final int[][] DIRECTIONS = {
        {0, 1}, {1, 0}, {1, 1}, {1, -1}, // right, down, down-right, down-left
        {0, -1}, {-1, 0}, {-1, -1}, {-1, 1} // left, up, up-left, up-right
    };

    public String solveFirst() throws IOException {
        List<String> lines = fileService.readFileLines(INPUT_FILE);
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
        return String.valueOf(count);
    }

    private int countMatches(char[][] grid, int row, int col) {
        int count = 0;
        for (int[] dir : DIRECTIONS) {
            count += checkDirection(grid, row, col, dir[0], dir[1]);
            count += checkDirection(grid, row, col, -dir[0], -dir[1]); // Check opposite direction
        }
        return count;
    }

    private int checkDirection(char[][] grid, int row, int col, int rowInc, int colInc) {
        for (int k = 1; k < TARGET_WORD.length; k++) {
            int newRow = row + k * rowInc;
            int newCol = col + k * colInc;
            if (newRow < 0 || newRow >= grid.length || newCol < 0 || newCol >= grid[0].length || grid[newRow][newCol] != TARGET_WORD[k]) {
                return 0;
            }
        }
        return 1;
    }

    public String solveSecond() throws IOException {
        List<String> lines = fileService.readFileLines(INPUT_FILE);
        char[][] grid = lines.stream()
            .map(String::toCharArray)
            .toArray(char[][]::new);

        int count = 0;
        for (int i = 1; i < grid.length - 1; i++) {
            for (int j = 1; j < grid[i].length - 1; j++) {
                if (grid[i][j] == 'A') {
                    count += checkXMASPattern(grid, i, j);
                }
            }
        }
        return String.valueOf(count);
    }

    private int checkXMASPattern(char[][] grid, int row, int col) {
        // Check all rotations of the X-MAS pattern
        if (isXMAS(grid, row, col, 1, 0) && isXMAS(grid, row, col, 0, 1)) {
            return 1;
        }
        if (isXMAS(grid, row, col, 1, 0) && isXMAS(grid, row, col, 0, -1)) {
            return 1;
        }
        if (isXMAS(grid, row, col, -1, 0) && isXMAS(grid, row, col, 0, 1)) {
            return 1;
        }
        if (isXMAS(grid, row, col, -1, 0) && isXMAS(grid, row, col, 0, -1)) {
            return 1;
        }
        return 0;
    }

    private boolean isXMAS(char[][] grid, int row, int col, int rowInc, int colInc) {
        char first = grid[row + rowInc][col + colInc];
        char second = grid[row - rowInc][col - colInc];
        return (first == 'M' && second == 'S') || (first == 'S' && second == 'M');
    }

}
