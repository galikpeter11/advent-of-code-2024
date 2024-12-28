package day14;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Part2 {
    private static final Pattern pattern = Pattern.compile("\\d+");


    public static void main(String[] args) throws IOException {
        List<Machine> machines = parseInput();

        long result = 0;
        for (Machine m : machines) {
            long i = (m.p.y * m.b.x - m.p.x * m.b.y) / (m.a.y * m.b.x - m.a.x * m.b.y);
            long j = (m.p.y * m.a.x - m.p.x * m.a.y) / (m.b.y * m.a.x - m.b.x * m.a.y);
            if (i * m.a.x + j * m.b.x == m.p.x && i * m.a.y + j * m.b.y == m.p.y) {
                result += 3 * i + j;
            }
        }
        System.out.println(result);
    }

    private static List<Machine> parseInput() throws IOException {
        BufferedReader f = new BufferedReader(new InputStreamReader(System.in));
        List<Machine> result = new ArrayList<>();
        String line;
        Machine currentMachine = new Machine();
        while ((line = f.readLine()) != null) {
            if (line.startsWith("Button A:")) {
                currentMachine.a = parseVec(line);
            } else if (line.startsWith("Button B:")) {
                currentMachine.b = parseVec(line);
            } else if (line.startsWith("Prize:")) {
                currentMachine.p = parseVec(line);
                currentMachine.p.x += 10000000000000L;
                currentMachine.p.y += 10000000000000L;
                result.add(currentMachine);
                currentMachine = new Machine();
            }
        }
        return result;
    }

    private static Vec parseVec(final String line) {
        try {
            String[] parts = line.split("[^\\d-]+");
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            return new Vec(x, y);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Vec from line: " + line);
        }
    }

    private static class Machine {
        Vec a;
        Vec b;
        Vec p;

        @Override
        public String toString() {
            return "Machine{" +
                    "a=" + a +
                    ", b=" + b +
                    ", p=" + p +
                    '}';
        }
    }

    private static class Vec {
        long x, y;

        Vec(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return '(' +
                    "x=" + x +
                    ", y=" + y +
                    ')';
        }
    }
}