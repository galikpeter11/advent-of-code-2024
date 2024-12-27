package day07;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Part2 {

    public static void main(String[] args) throws IOException {
        Map<BigInteger, List<BigInteger>> input = parseInput();
        BigInteger result = BigInteger.ZERO;
        for (Map.Entry<BigInteger, List<BigInteger>> entry : input.entrySet()) {
            BigInteger target = entry.getKey();
            List<BigInteger> operands = entry.getValue();
            if (canBeCalculated(target, operands)) {
                result = result.add(target);
            }
        }
        System.out.println(result);
    }

    private static boolean canBeCalculated(BigInteger target, List<BigInteger> operands) {
        return calculatePossibleValues(operands, 0, operands.getFirst(), target);
    }

    private static boolean calculatePossibleValues(List<BigInteger> operands, int index, BigInteger currentResult, BigInteger target) {
        if (index == operands.size() - 1) {
            return currentResult.equals(target);
        }

        boolean sumResult = calculatePossibleValues(operands, index + 1, currentResult.add(operands.get(index + 1)), target);
        boolean multResult = calculatePossibleValues(operands, index + 1, currentResult.multiply(operands.get(index + 1)), target);
        boolean concatResult = calculatePossibleValues(operands, index + 1, BigInteger.valueOf(Long.parseLong("" + currentResult + operands.get(index + 1))), target);

        return sumResult || multResult || concatResult;
    }

    private static Map<BigInteger, List<BigInteger>> parseInput() throws IOException {
        Map<BigInteger, List<BigInteger>> result = new HashMap<>();
        BufferedReader f = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = f.readLine()) != null) {
            String[] split = line.split(":");
            BigInteger key = BigInteger.valueOf(Long.parseLong(split[0]));
            List<BigInteger> value = new ArrayList<>();
            String[] split1 = split[1].trim().split(" ");
            for (String s : split1) {
                value.add(BigInteger.valueOf(Long.parseLong(s.strip())));
            }
            result.put(key, value);
        }
        return result;
    }
}
