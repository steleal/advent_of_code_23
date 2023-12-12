package day12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main01 {
    public static final String START = "D:\\1\\advent_of_code\\2023_12\\src\\main\\resources\\";

    public static void main(String[] args) throws IOException {
        new Main01().run();
    }

    private void run() throws IOException {
        List<String> rows = Files.readAllLines(Paths.get(START + "day12\\input.txt"));
        List<SpringMapRow> springs = rows.stream()
                .map(this::strToStringRow)
                .collect(Collectors.toList());
        long result = springs.stream()
                .mapToLong(this::calcArrangements)
                .sum();
        System.out.println("Part 1: " + result);
    }

    private long calcArrangements(SpringMapRow springMapRow) {
        int questCnt = 0;
        int numCnt = 0;
        for (char c : springMapRow.springs) {
            questCnt += c == '?' ? 1 : 0;
            numCnt += c == '#' ? 1 : 0;
        }
        int damagedCnt = 0;
        for (int dg : springMapRow.damagedGroups) {
            damagedCnt += dg;
        }
        if (numCnt == damagedCnt) return 1;
        if (damagedCnt + springMapRow.damagedGroups.length - 1 == springMapRow.springs.length) return 1;

        int unknownNumsCnt = damagedCnt - numCnt;
        char[] test = new char[questCnt];
        Arrays.fill(test, 0, unknownNumsCnt, '#');
        Arrays.fill(test, unknownNumsCnt, questCnt, '.');
        int sum = 0;
        do {
            char[] tt = replaceQuestOnTest(springMapRow.springs, test);
            if (isSatisfied(tt, springMapRow.damagedGroups)) sum++;
        } while (nextCombination(test, test.length));

        return sum;
    }

    private char[] replaceQuestOnTest(char[] origin, char[] test) {
        char[] chars = origin.clone();
        int testI = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '?') chars[i] = test[testI++];
        }
        return chars;
    }

    /**
     * Меняет массив a, возвращает true, если перестановка удалась.
     */
    private boolean nextCombination(char[] a, int n) {
        int j = n - 2;
        while (j != -1 && a[j] >= a[j + 1]) j--;
        if (j == -1)
            return false; // больше перестановок нет
        int k = n - 1;
        while (a[j] >= a[k]) k--;
        swap(a, j, k);
        int l = j + 1, r = n - 1; // сортируем оставшуюся часть последовательности
        while (l < r)
            swap(a, l++, r--);
        return true;
    }

    private void swap(char[] a, int i, int j) {
        char s = a[i];
        a[i] = a[j];
        a[j] = s;
    }

    private boolean isSatisfied(char[] allSprings, int[] damagedSpringCounts) {
        int di = 0;
        int tmpDCnt = allSprings[0] == '#' ? 1 : 0;
        for (int i = 1; i < allSprings.length; i++) {
            char curr = allSprings[i];
            char prev = allSprings[i - 1];
            if (curr == '#') tmpDCnt++;
            if ((prev == '#' && curr == '.') || (curr == '#' && i == allSprings.length - 1)) {
                if (di == damagedSpringCounts.length || damagedSpringCounts[di] != tmpDCnt) {
                    return false;
                }
                di++;
                tmpDCnt = 0;
            }
        }
        return di == damagedSpringCounts.length;
    }

    private SpringMapRow strToStringRow(String str) {
        String[] arr = str.split(" ");
        String[] groups = arr[1].split(",");
        int[] damagedGroups = new int[groups.length];
        for (int i = 0; i < groups.length; i++) {
            damagedGroups[i] = Integer.parseInt(groups[i]);
        }
        return new SpringMapRow(arr[0].toCharArray(), damagedGroups);
    }

    static class SpringMapRow {
        char[] springs;
        int[] damagedGroups;

        public SpringMapRow(char[] springs, int[] damagedGroups) {
            this.springs = springs;
            this.damagedGroups = damagedGroups;
        }
    }
}
