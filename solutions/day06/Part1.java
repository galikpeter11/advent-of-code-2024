package day06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class Part1 {
    static Vector<Pair<Integer, Integer>> dirs = new Vector<>(List.of(new Pair<>(-1, 0), new Pair<>(0, 1), new Pair<>(1, 0), new Pair<>(0, -1)));

    public static void main(String[] args) throws IOException {
        BufferedReader f = new BufferedReader(new InputStreamReader(System.in));
        String line;
        List<String> lines = new ArrayList<>();
        while ((line = f.readLine()) != null) {
            lines.add(line);
        }

        char[][] grid = lines.stream().map(String::toCharArray).toArray(char[][]::new);

        int H = grid.length;
        int W = grid[0].length;

        // Find the starting point
        Pair<Integer, Integer> me = new Pair<>(-1, -1);
        Set<Pair<Integer, Integer>> visited = new HashSet<>();
        int dir = 0;
        for (int row = 0; row < H; row++) {
            for (int col = 0; col < W; col++) {
                if (grid[row][col] == '^') {
                    me = new Pair<>(row, col);
                    visited.add(me);
                    grid[row][col] = '.';
                }
            }
        }

        while (true) {
            int r2 = me.first + dirs.get(dir).first;
            int c2 = me.second + dirs.get(dir).second;

            if (!(0 <= r2 && r2 < H && 0 <= c2 && c2 < W)) {
                break;
            }

            if (grid[r2][c2] == '.') {
                me = new Pair<>(r2, c2);
                visited.add(me);
            } else {
                dir = (dir + 1) % 4;
            }
        }

        //Print the final grid status
        for (int row = 0; row < H; row++) {
            for (int col = 0; col < W; col++) {
                if (visited.contains(new Pair<>(row, col))) {
                    System.out.print("X");
                } else {
                    if (grid[row][col] == '#') {
                        System.out.print("#");
                    } else {
                        System.out.print(".");
                    }
                }
            }
            System.out.println();
        }

        System.out.println(visited.size());
    }

    private static class Pair<T1, T2> {
        public T1 first;
        public T2 second;

        public Pair(T1 first, T2 second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Pair<?, ?> pair = (Pair<?, ?>) o;
            return Objects.equals(first, pair.first) && Objects.equals(second, pair.second);
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }
    }
}
