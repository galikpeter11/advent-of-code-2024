package day02;

import static java.lang.Math.abs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Part1 {
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
        return check(IntStream.range(0, arr.length).filter(i -> i != index).map(i -> arr[i]).toArray());
    }

    public static void main(String[] args) throws IOException {
        long result = 0L;

        BufferedReader f = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = f.readLine()) != null) {
            int[] a = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();

            if (check(a)) {
                result++;
            }
        }

        System.out.println(result);
    }
}
