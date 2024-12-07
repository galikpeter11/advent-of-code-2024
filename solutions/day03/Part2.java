package day03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Part2 {
    public static void main(String[] args) throws IOException {

        BufferedReader f = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = f.readLine()) != null) {
            sb.append(line);
        }
        sb.append("##########################");
        char[] input = sb.toString().toCharArray();

        int n = input.length;
        int answer = 0;
        boolean enabled = true;
        for (int i = 0; i < n; i++) {
            if (input[i] == 'd' && input[i + 1] == 'o' && input[i + 2] == '(' && input[i + 3] == ')') {
                enabled = true;
                i += 4;
            }
            if (input[i] == 'd' && input[i + 1] == 'o' && input[i + 2] == 'n' && input[i + 3] == '\'' && input[i + 4] == 't' && input[i + 5] == '('
                && input[i + 6] == ')') {
                enabled = false;
                i += 7;
            }
            if (input[i] == 'm') {
                if (input[i + 1] == 'u' && input[i + 2] == 'l' && input[i + 3] == '(') {
                    i += 4;
                    int[] ref1 = {i};
                    int x = getNumberAt(input, ref1);
                    i = ref1[0];
                    if (input[i] == ',') {
                        i += 1;
                        int[] ref2 = {i};
                        int y = getNumberAt(input, ref2);
                        i = ref2[0];
                        if (input[i] == ')') {
                            if (x != -1 && y != -1) {
                                if (enabled) {
                                    answer += x * y;
                                }
                            }
                        }
                    }
                }
            }
        }

        System.out.println(answer);
    }

    private static int getNumberAt(char[] input, int[] i) {
        int result = 0;

        while (Character.isDigit(input[i[0]])) {
            result = result * 10 + (input[i[0]] - '0');
            i[0]++;
        }

        return result;
    }
}
