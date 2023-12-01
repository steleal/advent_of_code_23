package day01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main01 {
    public static final String RES = "D:\\1\\advent_of_code\\2023_12\\src\\main\\resources\\";

    public static void main(String[] args) throws IOException {
        new Main01().run();
    }

    private void run() throws IOException {
        int result = Files.readAllLines(Paths.get(RES + "day01\\input.txt")).stream()
                .mapToInt(this::toNumber)
                .sum();
        System.out.println(result);
    }

    private int toNumber(String str) {
        char[] chars = str.toCharArray();
        int c1 = 0;
        int c2 = 0;
        for (char aChar : chars) {
            if (Character.isDigit(aChar)) {
                c1 = aChar - '0';
                break;
            }
        }
        for (int i = chars.length - 1; i >= 0; i--) {
            char a = chars[i];
            if (Character.isDigit(a)) {
                c2 = a - '0';
                break;
            }
        }
        return c1 * 10 + c2;
    }
}
