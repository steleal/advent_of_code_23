package day03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main02 {
    public static final String START = "D:\\1\\advent_of_code\\2023_12\\src\\main\\resources\\";

    public static void main(String[] args) throws IOException {
        new Main02().run();
    }

    private void run() throws IOException {
        List<String> strings = Files.readAllLines(Paths.get(START + "day03\\input.txt"));
        char[][] chars = getArr2D(strings);

        // находим все числа рядом с '*' , запомним в мапу - ИД звездочки, список чисел.
        Map<Integer, List<Integer>> asterNums = new HashMap<>();
        for (int i = 0; i < chars.length; i++) {
            for (int j = 0; j < chars[i].length; j++) {
                char c = chars[i][j];
                if (Character.isDigit(c)) {
                    // начало и конец числа.
                    int numStart = j;
                    int numEnd = findEndNum(chars[i], j);
                    // если рядом с числом есть '*', запомним число в мапу.
                    int asterId = findAsteriksId(chars, i, numStart, numEnd);
                    if (asterId > 0) {
                        int number = getNumber(chars[i], numStart, numEnd);
                        List<Integer> nums = asterNums.getOrDefault(asterId, new ArrayList<>());
                        nums.add(number);
                        asterNums.put(asterId, nums);
                    }
                    // двинем указатель на последнюю цифру числа
                    j = numEnd;
                }
            }
        }
        // найдем сумму всех gear ratio.
        int result = 0;
        for (Map.Entry<Integer, List<Integer>> gear : asterNums.entrySet()) {
            List<Integer> nums = gear.getValue();
            if (nums.size() == 2) {
                // Это gear, добавим gear ratio.
                result += nums.get(0) * nums.get(1);
            }
        }

        System.out.println(result);
    }

    private int getNumber(char[] chars, int numStart, int numEnd) {
        String str = String.valueOf(chars, numStart, numEnd + 1 - numStart);
        return Integer.parseInt(str);
    }

    /**
     * Ищем '*' в соседних с числом символах.
     * Вернем 'у * columns + x' в качестве ID для первой находки.
     * У каждого числа - не более одного '*', и это хорошо.
     */
    private int findAsteriksId(char[][] chars, int row, int numStart, int numEnd) {
        int checkStart = Math.max(0, numStart - 1);
        int checkEnd = Math.min(chars.length - 1, numEnd + 1);
        // сверху
        if (row > 0) {
            for (int i = checkStart; i <= checkEnd; i++) {
                char c = chars[row - 1][i];
                if (isAsteriks(c)) {
                    return (row - 1) * chars[row].length + i;
                }
            }
        }
        // снизу
        if (row < chars.length - 1) {
            for (int i = checkStart; i <= checkEnd; i++) {
                char c = chars[row + 1][i];
                if (isAsteriks(c)) {
                    return (row + 1) * chars[row].length + i;
                }
            }
        }
        // слева
        if (checkStart < numStart && isAsteriks(chars[row][checkStart])) return row * chars[row].length + checkStart;
        // справа
        if (checkEnd > numEnd && isAsteriks(chars[row][checkEnd])) return row * chars[row].length + checkEnd;

        return -1;
    }

    private boolean isAsteriks(char c) {
        return c == '*';
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

}
