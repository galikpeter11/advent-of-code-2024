package com.pgalik.AdventOfCode.day01;

import com.pgalik.AdventOfCode.FileService;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

@org.springframework.stereotype.Service("day01Service")
@RequiredArgsConstructor
public class Service {
    public static final String INPUT_FILE = "inputs/day01.txt";
    private final FileService fileService;

    public String solveFirst() throws IOException {
        Vector<Integer> a = new Vector<>(), b = new Vector<>();
        fileService.readFileLines(INPUT_FILE).forEach(line -> {
            String[] numbers = line.split("   ");
            a.add(Integer.parseInt(numbers[0]));
            b.add(Integer.parseInt(numbers[1]));
        });

        a.sort(Integer::compareTo);
        b.sort(Integer::compareTo);

        long result = 0L;

        for (int i = 0; i < a.size(); i++) {
            result += Math.abs(a.get(i) - b.get(i));
        }

        return Long.toString(result);
    }

    public String solveSecond() throws IOException {
        Vector<Integer> a = new Vector<>(), b = new Vector<>();
        fileService.readFileLines(INPUT_FILE).forEach(line -> {
            String[] numbers = line.split("   ");
            a.add(Integer.parseInt(numbers[0]));
            b.add(Integer.parseInt(numbers[1]));
        });

        a.sort(Integer::compareTo);
        b.sort(Integer::compareTo);

        Map<Integer, Integer> freq = new HashMap<>();
        b.forEach(integer -> freq.put(integer, freq.getOrDefault(integer, 0) + 1));

        return a.parallelStream().mapToInt(num -> num * freq.getOrDefault(num,0)).sum() + "";
    }
}
