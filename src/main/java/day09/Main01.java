package day09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Main01 {
    public static final String START = "D:\\1\\advent_of_code\\2023_12\\src\\main\\resources\\";

    public static void main(String[] args) throws IOException {
        new Main01().run();
    }

    private void run() throws IOException {
        List<String> strings = Files.readAllLines(Paths.get(START + "day09\\input.txt"));

        long result = strings.stream()
                .map(this::stringToLongArr)
                .map(this::calculateHistoryList)
                .mapToLong(this::calculateNextValue)
                .sum();
        System.out.println("Part 1: " + result);

        long result2 = strings.stream()
                .map(this::stringToLongArr)
                .map(this::calculateHistoryList)
                .mapToLong(this::calculatePreviousValue)
                .sum();

        System.out.println("Part 2: " + result2);
    }

    private long calculatePreviousValue(List<long[]> list) {
        Collections.reverse(list);
        AtomicLong result = new AtomicLong(0);
        list.forEach(arr -> result.set(arr[0] - result.get()));
        return result.get();
    }

    private long calculateNextValue(List<long[]> list) {
        Collections.reverse(list);
        AtomicLong result = new AtomicLong(0);
        list.forEach(arr -> result.addAndGet(arr[arr.length - 1]));
        return result.get();
    }

    private List<long[]> calculateHistoryList(long[] ss) {
        List<long[]> list = new ArrayList<>();
        list.add(ss);
        while (true) {
            long[] arr = list.get(list.size() - 1);
            long[] diff = new long[arr.length - 1];
            boolean isAllZeroes = true;
            for (int i = 0; i < diff.length; i++) {
                diff[i] = arr[i + 1] - arr[i];
                isAllZeroes &= diff[i] == 0;
            }
            if (isAllZeroes) break;
            list.add(diff);
        }
        return list;
    }

    private long[] stringToLongArr(String str) {
        String[] s = str.split(" ");
        long[] ss = new long[s.length];
        for (int i = 0; i < s.length; i++) {
            ss[i] = Long.parseLong(s[i]);
        }
        return ss;
    }

}
