package day05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Part1 {
    private static final List<Rule> rules = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader f = new BufferedReader(new InputStreamReader(System.in));
        String line;
        List<List<Integer>> prints = new ArrayList<>();
        while ((line = f.readLine()) != null) {
            if (line.contains("|")) {
                String[] split = line.split("\\|");
                rules.add(new Rule(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
            }

            if (line.contains(",")) {
                String[] split = line.split(",");
                List<Integer> print = new ArrayList<>();
                for (String s : split) {
                    print.add(Integer.parseInt(s));
                }
                prints.add(print);
            }
        }
        long result = 0;
        for (List<Integer> print : prints) {
            boolean goodOrder = true;
            for (int i = 0; i < print.size() - 1; i++) {
                if (!isInGoodOrder(print.get(i), print.get(i + 1))) {
                    goodOrder = false;
                    break;
                }
            }

            if (goodOrder) {
                result += print.get(print.size() / 2);
            }
        }
        System.out.println(result);
    }

    private static boolean isInGoodOrder(final Integer left, final Integer right) {
        for (Rule rule : rules) {
            if (Objects.equals(rule.left(), left) && Objects.equals(rule.right(), right)) {
                return true;
            }
            if (Objects.equals(rule.right(), left) && Objects.equals(rule.left(), right)) {
                return false;
            }
        }
        return true;
    }

    record Rule(Integer left, Integer right) {
    }
}
