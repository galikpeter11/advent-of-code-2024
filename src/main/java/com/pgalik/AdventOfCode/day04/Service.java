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

    public String solveFirst() throws IOException {
        List<String> lines = fileService.readFileLines(INPUT_FILE);
        char[][] input = new char[lines.size()][lines.getFirst().length()];
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {
                input[i][j] = lines.get(i).charAt(j);
            }
        }

        int result = 0;

        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {
                if (input[i][j] == 'X') {
                    result += countHorizontalMatch(input, i, j);
                    result += countVerticalMatch(input, i, j);
                    result += countDiagonalMatch(input, i, j);
                }
            }
        }

        return String.valueOf(result);
    }

    private int countHorizontalMatch(char[][] input, int i, int j) {
        int result = 0;
        try {
            // Left to right
            if (input[i][j + 1] == 'M') {
                if (input[i][j + 2] == 'A') {
                    if (input[i][j + 3] == 'S') {
                        result++;
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            // Do nothing
        }

        try {
            // Right to left
            if (input[i][j - 1] == 'M') {
                if (input[i][j - 2] == 'A') {
                    if (input[i][j - 3] == 'S') {
                        result++;
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            // Do nothing
        }

        return result;
    }

    private int countVerticalMatch(char[][] input, int i, int j) {
        int result = 0;

        try {
            // Top to bottom
            if (input[i + 1][j] == 'M') {
                if (input[i + 2][j] == 'A') {
                    if (input[i + 3][j] == 'S') {
                        result++;
                    }
                }
            }

        } catch (IndexOutOfBoundsException e) {
            // Do nothing
        }
        try {
            // Bottom to top
            if (input[i - 1][j] == 'M') {
                if (input[i - 2][j] == 'A') {
                    if (input[i - 3][j] == 'S') {
                        result++;
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            // Do nothing
        }

        return result;
    }

    private int countDiagonalMatch(char[][] input, int i, int j) {
        int result = 0;
        try {
            // Left top to right bottom
            if (input[i + 1][j + 1] == 'M') {
                if (input[i + 2][j + 2] == 'A') {
                    if (input[i + 3][j + 3] == 'S') {
                        result++;
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            // Do nothing
        }
        try {
            // Left top to right bottom
            if (input[i + 1][j - 1] == 'M') {
                if (input[i + 2][j - 2] == 'A') {
                    if (input[i + 3][j - 3] == 'S') {
                        result++;
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            // Do nothing
        }
        try {
            // Left top to right bottom
            if (input[i - 1][j + 1] == 'M') {
                if (input[i - 2][j + 2] == 'A') {
                    if (input[i - 3][j + 3] == 'S') {
                        result++;
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            // Do nothing
        }
        try {
            // Left top to right bottom
            if (input[i - 1][j - 1] == 'M') {
                if (input[i - 2][j - 2] == 'A') {
                    if (input[i - 3][j - 3] == 'S') {
                        result++;
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            // Do nothing
        }

        return result;
    }

    public String solveSecond() throws IOException {
        List<String> lines = fileService.readFileLines(INPUT_FILE);
        char[][] input = new char[lines.size()][lines.getFirst().length()];
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {
                input[i][j] = lines.get(i).charAt(j);
            }
        }

        int result = 0;

        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {
                if (input[i][j] == 'A') {
                    try {
                        if (input[i - 1][j - 1] == 'M'
                            && input[i + 1][j - 1] == 'M'
                            && input[i + 1][j + 1] == 'S'
                            && input[i - 1][j + 1] == 'S'
                        ) {
                            result++;
                        }
                    } catch (IndexOutOfBoundsException e) {
                        // Do nothing
                    }try{
                            if (input[i - 1][j - 1] == 'S'
                            && input[i + 1][j - 1] == 'M'
                            && input[i + 1][j + 1] == 'M'
                            && input[i - 1][j + 1] == 'S'
                        ) {
                        result++;
                    }
                } catch (IndexOutOfBoundsException e) {
                    // Do nothing
                }try{
                            if(input[i - 1][j - 1] == 'S'
                            && input[i + 1][j - 1] == 'S'
                            && input[i + 1][j + 1] == 'M'
                            && input[i - 1][j + 1] == 'M'
                        ) {
                    result++;
                }
            } catch (IndexOutOfBoundsException e) {
                // Do nothing
            }
                            try{ if (input[i - 1][j - 1] == 'M'
                            && input[i + 1][j - 1] == 'S'
                            && input[i + 1][j + 1] == 'S'
                            && input[i - 1][j + 1] == 'M'
                        ) {
                            result++;
                        }
                    } catch (IndexOutOfBoundsException e) {
                        // Do nothing
                    }
                }
            }
        }

        return String.valueOf(result);
    }

}
