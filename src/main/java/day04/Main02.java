package day04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Main02 {
    public static final String START = "D:\\1\\advent_of_code\\2023_12\\src\\main\\resources\\";

    public static void main(String[] args) throws IOException {
        new Main02().run();
    }

    private void run() throws IOException {
        List<String> strings = Files.readAllLines(Paths.get(START + "day04\\input.txt"));
        int strCnt = strings.size();
        int[] cardCounts = getInitCardCount(strCnt);
        for (int i = 0; i < strCnt; i++) {
            int winCardCount = getWinCardCount(strings.get(i));
            addWinCards(cardCounts, i, winCardCount);
        }
        int result = Arrays.stream(cardCounts).sum();

        System.out.println(result);
    }

    private void addWinCards(int[] cardCount, int startId, int winCardsNumber) {
        int k = cardCount[startId];
        for (int i = 1; i <= winCardsNumber; i++) {
            if (cardCount.length == startId + i) break;
            cardCount[startId + i] += k;
        }
    }

    private int[] getInitCardCount(int strCnt) {
        int[] cardCount = new int[strCnt];
        Arrays.fill(cardCount, 1);
        return cardCount;
    }

    private int getWinCardCount(String s) {
        String[] cardParts = s.split("[:|]");
        Set<Integer> winNumbers = getWinNumbers(cardParts[1].trim());
        List<Integer> checkNumbers = getCheckNumbers(cardParts[2].trim());
        return (int) checkNumbers.stream()
                .filter(winNumbers::contains)
                .count();
    }

    private Set<Integer> getWinNumbers(String s) {
        return Arrays.stream(s.split(" "))
                .filter(s1 -> s1.trim().length() > 0)
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
    }

    private List<Integer> getCheckNumbers(String s) {
        return Arrays.stream(s.split(" "))
                .filter(s1 -> s1.trim().length() > 0)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

}
