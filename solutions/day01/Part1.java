import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

public class Part1 {
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

        long result = 0L;

        for (int i = 0; i < a.size(); i++) {
            result += Math.abs(a.get(i) - b.get(i));
        }

        System.out.println(result);
    }
}
