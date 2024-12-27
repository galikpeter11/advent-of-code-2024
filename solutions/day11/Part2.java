package day11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Part2 {
    private static final int NUMBER_OF_BLINKS = 75;
    private static Map<BigInteger, BigInteger> cache = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader f = new BufferedReader(new InputStreamReader(System.in));
        Arrays.stream(f.readLine().split(" ")).forEach(s -> cache.put(BigInteger.valueOf(Long.parseLong(s)), BigInteger.ONE));

        for (int i = 1; i < NUMBER_OF_BLINKS + 1; i++) {
            Map<BigInteger, BigInteger> newCache = new HashMap<>();
            for (Map.Entry<BigInteger, BigInteger> entry : cache.entrySet()) {

                BigInteger stone = entry.getKey();
                BigInteger count = entry.getValue();

                if (stone.equals(BigInteger.ZERO)) {
                    newCache.compute(BigInteger.ONE, (k, v) -> v == null ? count : v.add(count));
                    continue;
                }

                String stoneString = stone + "";
                if (stoneString.length() % 2 == 0) {
                    newCache.compute(BigInteger.valueOf(Long.parseLong(stoneString.substring(0, stoneString.length() / 2))), (k, v) -> v == null ? count : v.add(count));
                    newCache.compute(BigInteger.valueOf(Long.parseLong(stoneString.substring(stoneString.length() / 2))), (k, v) -> v == null ? count : v.add(count));
                    continue;
                }

                newCache.compute(stone.multiply(BigInteger.valueOf(2024L)), (k, v) -> v == null ? count : v.add(count));
            }

            cache = newCache;
            BigInteger result = cache.values().stream().reduce(BigInteger.ZERO, BigInteger::add);
            System.out.println(i + ": " + cache.size() + " " + result);
        }
    }
}
