package day04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Main01 {
    public static final String START = "D:\\1\\advent_of_code\\2023_12\\src\\main\\resources\\";

    public static void main(String[] args) throws IOException {
        new Main01().run();
    }

    private void run() throws IOException {
        List<String> strings = Files.readAllLines(Paths.get(START + "day04\\input.txt"));
//        List<String> strings = getTest();
        int result = strings.stream()
                .mapToInt(this::getCardPrice)
                .sum();

        System.out.println(result);
    }

    private int getCardPrice(String s) {
        String[] cardParts = s.split("[:|]");
        if (cardParts.length < 3) return 0;
        Set<Integer> winNumbers = getWinNumbers(cardParts[1].trim());
        List<Integer> checkNumbers = getCheckNumbers(cardParts[2].trim());
        int price = 0;
        for (Integer number : checkNumbers) {
            if (winNumbers.contains(number)) {
                price = price == 0 ? 1 : price * 2;
            }
        }
        return price;
    }

    private Set<Integer> getWinNumbers(String s) {
        Set<Integer> winNumbers = new HashSet<>();
        for (String strWinNumber : s.split(" ")) {
            if (strWinNumber.trim().length() == 0) continue;
            winNumbers.add(Integer.parseInt(strWinNumber));
        }
        return winNumbers;
    }

    private List<Integer> getCheckNumbers(String s) {
        return Arrays.stream(s.split(" "))
                .filter(s1 -> s1.trim().length() > 0)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private List<String> getTest() {
        String str = """
                Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
                Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
                Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
                Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
                Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
                Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
                """;
        return Arrays.asList(str.split("\n"));
    }

}
