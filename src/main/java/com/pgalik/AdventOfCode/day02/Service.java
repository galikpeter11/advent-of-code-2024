package com.pgalik.AdventOfCode.day02;

import com.pgalik.AdventOfCode.FileService;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

import static java.lang.Math.abs;

@org.springframework.stereotype.Service("day02Service")
@RequiredArgsConstructor
public class Service {
    public static final String INPUT_FILE = "inputs/day02.txt";
    private final FileService fileService;

    private static boolean check(int[] a) {
        int k = a.length - 1;
        boolean ok = true;
        boolean onlyInc = true;
        boolean onlyDec = true;
        for (int i = 0; i < k; i++) {
            int diff = a[i + 1] - a[i];
            if (diff > 0) {
                onlyDec = false;
            } else if (diff < 0) {
                onlyInc = false;
            }
            if (!(1 <= abs(diff) && abs(diff) <= 3)) {
                ok = false;
                break;
            }
        }
        return ok && (onlyInc || onlyDec);
    }

    private static boolean checkWithRemovingElement(int[] arr, int index) {
        return check(IntStream.range(0, arr.length)
                .filter(i -> i != index)
                .map(i -> arr[i])
                .toArray());
    }

    public String solveFirst() throws IOException {
        long result = 0L;

        for (String line : fileService.readFileLines(INPUT_FILE)) {
            int[] a = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();

            if (check(a)) {
                result++;
            }
        }

        return Long.toString(result);
    }

    public String solveSecond() throws IOException {
        long result = 0L;

        for (String line : fileService.readFileLines(INPUT_FILE)) {
            int[] a = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
            boolean anyOk = false;
            int k = a.length - 1;
            anyOk |= checkWithRemovingElement(a, 0);
            for (int i = 0; i <= k - 1; i++) {
                int diff = a[i + 1] - a[i];
                if (abs(diff) < 1 || abs(diff) > 3) {
                    anyOk |= checkWithRemovingElement(a, i);
                    anyOk |= checkWithRemovingElement(a, i + 1);
                    break;
                }
                if (i + 2 <= k) {
                    int diff2 = a[i + 2] - a[i + 1];
                    if ((diff > 0) != (diff2 > 0)) {
                        anyOk |= checkWithRemovingElement(a, i);
                        anyOk |= checkWithRemovingElement(a, i + 1);
                        anyOk |= checkWithRemovingElement(a, i + 2);
                        break;
                    }
                }
            }

            if (anyOk) {
                result++;
            }
        }

        return Long.toString(result);
    }
}
