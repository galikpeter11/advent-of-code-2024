package day01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Part2 {
    public static void main(String[] args) throws IOException {
        Vector<Integer> a = new Vector<>(), b = new Vector<>();
        BufferedReader f = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = f.readLine()) != null) {
            String[] numbers = line.split("   ");
            a.add(Integer.parseInt(numbers[0]));
            b.add(Integer.parseInt(numbers[1]));
        }

        a.sort(Integer::compareTo);
        b.sort(Integer::compareTo);

        Map<Integer, Integer> freq = new HashMap<>();
        b.forEach(integer -> freq.put(integer, freq.getOrDefault(integer, 0) + 1));

        System.out.println(a.parallelStream().mapToInt(num -> num * freq.getOrDefault(num, 0)).sum() + "");
    }
}
