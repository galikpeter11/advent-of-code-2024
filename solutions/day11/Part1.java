package day11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Part1 {
    private static final int NUMBER_OF_BLINKS = 25;

    public static void main(String[] args) throws IOException {
        BufferedReader f = new BufferedReader(new InputStreamReader(System.in));
        long[] input = Arrays.stream(f.readLine().split(" ")).mapToLong(Long::parseLong).toArray();

        for (int i = 0; i < NUMBER_OF_BLINKS; i++) {
            input = simulate(input);
            System.out.println(input.length);
        }
    }

    private static long[] simulate(long[] input) {
        List<Long> result = new ArrayList<>();

        for (long stone : input) {
            if (stone == 0) {
                result.add(1L);
                continue;
            }

            String stoneString = stone + "";
            if (stoneString.length() % 2 == 0) {
                result.add(Long.parseLong(stoneString.substring(0, stoneString.length() / 2)));
                result.add(Long.parseLong(stoneString.substring( stoneString.length() / 2)));
                continue;
            }

            result.add(stone*2024L);
        }

        return result.stream().mapToLong(i -> i).toArray();
    }
}
