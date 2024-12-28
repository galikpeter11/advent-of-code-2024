package day14;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Part1 {
    private static final Pattern pattern = Pattern.compile("\\d+");
    private static final int WIDTH = 101;
    private static final int HEIGHT = 103;

    public static void main(String[] args) throws IOException {
        List<Robot> robots = parseInput();

        for (int i = 1; i < WIDTH * HEIGHT; i++) {
            // Move the robots
            for (Robot m : robots) {
                m.pos.x += m.vel.x;
                m.pos.x = ((m.pos.x % WIDTH) + WIDTH) % WIDTH;
                m.pos.y += m.vel.y;
                m.pos.y = ((m.pos.y % HEIGHT) + HEIGHT) % HEIGHT;
            }

            // Count the number of robots in each quadrant
            int tl = 0, tr = 0, bl = 0, br = 0;
            for (Robot m : robots) {
                if (m.pos.x < WIDTH / 2 && m.pos.y < HEIGHT / 2) {
                    tl++;
                } else if (m.pos.x > WIDTH / 2 && m.pos.y < HEIGHT / 2) {
                    tr++;
                } else if (m.pos.x < WIDTH / 2 && m.pos.y > HEIGHT / 2) {
                    bl++;
                } else if (m.pos.x > WIDTH / 2 && m.pos.y > HEIGHT / 2) {
                    br++;
                }
            }


            int[][] grid = new int[WIDTH][HEIGHT];
            for (Robot m : robots) {
                grid[m.pos.x][m.pos.y] += 1;
            }

            StringBuilder sb = new StringBuilder();
            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    sb.append(grid[x][y] > 0 ? "#" : ".");
                }
                sb.append("\n");
            }
            if (sb.toString().contains("###############")) {
                System.out.println("Seconds: " + i);
                System.out.println(sb);
            }
        }
    }

    private static List<Robot> parseInput() throws IOException {
        BufferedReader f = new BufferedReader(new InputStreamReader(System.in));
        List<Robot> result = new ArrayList<>();
        String line;
        while ((line = f.readLine()) != null) {

            String[] parts = line.split("[^\\d-]+");
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            int vx = Integer.parseInt(parts[3]);
            int vy = Integer.parseInt(parts[4]);

            result.add(new Robot(new Vec(x, y), new Vec(vx, vy)));
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

    private static class Robot {
        Vec pos;
        Vec vel;

        public Robot(Vec pos, Vec vel) {
            this.pos = pos;
            this.vel = vel;
        }

        @Override
        public String toString() {
            return "Robot{" + pos + ", " + vel + "}";
        }
    }

    private static class Vec {
        int x, y;

        Vec(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return '(' + "x=" + x + ", y=" + y + ')';
        }
    }
}