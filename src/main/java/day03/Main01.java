package day03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Main01 {
    public static final String START = "D:\\1\\advent_of_code\\2023_12\\src\\main\\resources\\";

    public static void main(String[] args) throws IOException {
        new Main01().run();
    }

    private void run() throws IOException {
        List<String> strings = Files.readAllLines(Paths.get(START + "day03\\input.txt"));
        char[][] chars = getArr2D(strings);
        int rows = chars.length;
        int columns = chars[0].length;

        int result = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                char c = chars[i][j];
                if (Character.isDigit(c)) {
                    int numStart = j;
                    int numEnd = findEndNum(chars[i], j);
                    j = numEnd;
                    if (isPartNumber(chars, i, numStart, numEnd)) {
                        result += getNumber(chars[i], numStart, numEnd);
                    }
                }
            }
        }

        System.out.println(result);
    }

    private int getNumber(char[] chars, int numStart, int numEnd) {
        String str = String.valueOf(chars, numStart, numEnd + 1 - numStart);
        return Integer.parseInt(str);
    }

    private boolean isPartNumber(char[][] chars, int row, int numStart, int numEnd) {
        int checkStart = Math.max(0, numStart - 1);
        int checkEnd = Math.min(chars.length - 1, numEnd + 1);
        if (row > 0) {
            for (int i = checkStart; i <= checkEnd; i++) {
                char c = chars[row - 1][i];
                if (isSymbol(c)) {
                    return true;
                }
            }
        }
        if (row < chars.length - 1) {
            for (int i = checkStart; i <= checkEnd; i++) {
                char c = chars[row + 1][i];
                if (isSymbol(c)) {
                    return true;
                }
            }
        }
        if (checkStart < numStart && isSymbol(chars[row][checkStart])) return true;
        if (checkEnd > numEnd && isSymbol(chars[row][checkEnd])) return true;

        return false;
    }

    private boolean isSymbol(char c) {
        return !Character.isDigit(c) && c != '.';
    }

    private int findEndNum(char[] chars, int j) {
        int columns = chars.length;
        int numEnd = j;
        for (int k = j + 1; k < columns; k++) {
            if (Character.isDigit(chars[k])) {
                numEnd = k;
            } else {
                break;
            }
        }
        return numEnd;
    }

    private char[][] getArr2D(List<String> strings) {
        int rows = (int) strings.stream().filter(s -> s.length() > 1).count();
        char[][] result = new char[rows][];
        for (int i = 0; i < rows; i++) {
            result[i] = strings.get(i).toCharArray();
        }
        return result;
    }


    private List<String> getTest() {
        String str = """
                467..114..
                ...*......
                ..35..633.
                ......#...
                617*......
                .....+.58.
                ..592.....
                ......755.
                ...$.*....
                .664.598..
                """;
        return Arrays.asList(str.split("\n"));
    }

}
