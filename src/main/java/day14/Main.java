package day14;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {
    public static final String START = "D:\\1\\advent_of_code\\2023_12\\src\\main\\resources\\";

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    private void run() throws IOException {
        String str = Files.readString(Paths.get(START + "day14\\input.txt"));
        char[][] origin = parseDish(str);

        char[][] dish = copy(origin);
        moveToTheNorth(dish);
        long result = calculateWeight(dish);
        System.out.println("Part 1: " + result);

        char[][] dish1 = copy(origin);
        char[][] dish2 = copy(origin);
        // вычисляем цикл.
        int cycleCnt = 0;
        for (int i = 0; i < 1000_000_000; i++) {
            makeCycle(dish1);
            makeCycle(dish2);
            makeCycle(dish2);
            if (deepEq(dish1, dish2)) {
                cycleCnt = i + 1;
                break;
            }
        }
        // добиваем остаток циклов.
        int last = 1000_000_000 % cycleCnt;
        for (int i = 0; i < last; i++) {
            makeCycle(dish1);
        }
        result = calculateWeight(dish1);
        System.out.println("Part 2: " + result);
    }

    private boolean deepEq(char[][] dish1, char[][] dish2) {
        int rowCnt = dish1.length;
        for (int i = 0; i < rowCnt; i++) {
            if (!Arrays.equals(dish1[i], dish2[i])) return false;
        }
        return true;
    }

    private void makeCycle(char[][] chars) {
        int rowCnt = chars.length;
        int colCnt = chars[0].length;
        for (int j = 0; j < colCnt; j++) {
            for (int i = 1; i < rowCnt; i++) {
                tryFallToNorth(chars, i, j);
            }
        }
        for (int i = 0; i < rowCnt; i++) {
            for (int j = 1; j < colCnt; j++) {
                tryFallToWest(chars, i, j);
            }
        }

        for (int j = 0; j < colCnt; j++) {
            for (int i = rowCnt - 2; i >= 0; i--) {
                tryFallToSouth(chars, i, j);
            }
        }
        for (int i = 0; i < rowCnt; i++) {
            for (int j = colCnt - 2; j >= 0; j--) {
                tryFallToEast(chars, i, j);
            }
        }
    }

    private void moveToTheNorth(char[][] chars) {
        int rowCnt = chars.length;
        int colCnt = chars[0].length;
        for (int j = 0; j < colCnt; j++) {
            for (int i = 1; i < rowCnt; i++) {
                tryFallToNorth(chars, i, j);
            }
        }

    }

    private void tryFallToNorth(char[][] chars, int i, int j) {
        while (i > 0 && chars[i][j] == 'O' && chars[i - 1][j] == '.') {
            chars[i - 1][j] = 'O';
            chars[i][j] = '.';
            i--;
        }
    }

    private void tryFallToSouth(char[][] chars, int i, int j) {
        int lastId = chars.length - 1;
        while (i < lastId && chars[i][j] == 'O' && chars[i + 1][j] == '.') {
            chars[i + 1][j] = 'O';
            chars[i][j] = '.';
            i++;
        }
    }

    private void tryFallToWest(char[][] chars, int i, int j) {
        while (j > 0 && chars[i][j] == 'O' && chars[i][j - 1] == '.') {
            chars[i][j - 1] = 'O';
            chars[i][j] = '.';
            j--;
        }
    }

    private void tryFallToEast(char[][] chars, int i, int j) {
        int lastId = chars[0].length - 1;
        while (j < lastId && chars[i][j] == 'O' && chars[i][j + 1] == '.') {
            chars[i][j + 1] = 'O';
            chars[i][j] = '.';
            j++;
        }
    }

    private char[][] parseDish(String str) {
        String[] rows = str.split("\n");
        char[][] chars = new char[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            chars[i] = rows[i].toCharArray();
        }
        return chars;
    }

    private long calculateWeight(char[][] dish) {
        int rowCnt = dish.length;
        long result = 0;
        for (int i = 0; i < rowCnt; i++) {
            int sum = 0;
            for (char c : dish[i]) {
                if (c == 'O') sum++;
            }
            result += (long) sum * (rowCnt - i);
        }
        return result;
    }

    private char[][] copy(char[][] chars) {
        int rowCnt = chars.length;
        char[][] result = new char[rowCnt][];
        for (int j = 0; j < rowCnt; j++) {
            result[j] = chars[j].clone();
        }
        return result;
    }

}
