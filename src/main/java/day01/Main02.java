package day01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Main02 {
    public static final String RES = "D:\\1\\advent_of_code\\2023_12\\src\\main\\resources\\";
    public static final String[] DIGITS = new String[]{
            "0000", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

    public static void main(String[] args) throws IOException {
        new Main02().run();
    }

    private void run() throws IOException {
//        List<String> strings = getTest();
        List<String> strings = Files.readAllLines(Paths.get(RES + "day01\\input.txt"));
        int result = strings.stream()
                .mapToInt(this::toNumber)
                .sum();
        System.out.println(result);
    }

    private int toNumber(String str) {
        char[] chars = str.toCharArray();
        int c1 = 0;
        int c2 = 0;
        for (int i = 0; i < chars.length; i++) {
            char a = chars[i];
            if (Character.isDigit(a)) {
                c1 = a - '0';
                break;
            }
            int b = getSpelledDigit(str, i);
            if (b > 0) {
                c1 = b;
                break;
            }
        }
        for (int i = chars.length - 1; i >= 0; i--) {
            char a = chars[i];
            if (Character.isDigit(a)) {
                c2 = a - '0';
                break;
            }
            int b = getSpelledDigitReverse(str, i);
            if (b > 0) {
                c2 = b;
                break;
            }
        }
        return c1 * 10 + c2;
    }

    private int getSpelledDigit(String str, int i) {
        int l = str.length() - i;
        for (int j = 0; j < DIGITS.length; j++) {
            String spelled = DIGITS[j];
            if (spelled.length() <= l && str.startsWith(spelled, i)) {
                return j;
            }
        }
        return 0;
    }

    private int getSpelledDigitReverse(String str, int i) {
        for (int j = 0; j < DIGITS.length; j++) {
            String spelled = DIGITS[j];
            int startI = i + 1 - spelled.length();
            if (str.startsWith(spelled, startI)) {
                return j;
            }
        }
        return 0;
    }

    private List<String> getTest() {
        return Arrays.asList(("""
                two1nine
                eightwothree
                abcone2threexyz
                xtwone3four
                4nineeightseven2
                zoneight234
                7pqrstsixteen
                """).split("\n"));
    }
}
