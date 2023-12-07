package day07;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Main02 {
    public static final String START = "D:\\1\\advent_of_code\\2023_12\\src\\main\\resources\\";

    public static void main(String[] args) throws IOException {
        new Main02().run();
    }

    private void run() throws IOException {
        List<String> strings = Files.readAllLines(Paths.get(START + "day07\\input.txt"));
        AtomicInteger id = new AtomicInteger(1);
        int result = strings.stream()
                .map(this::toHand)
                .sorted()
                .mapToInt(h -> id.getAndIncrement() * h.value)
                .sum();
        System.out.println("Part 2: " + result);
    }

    private Hand toHand(String s) {
        Hand hand = new Hand();
        String[] ss = s.split(" ");
        for (int i = 0; i < 5; i++) {
            char[] chars = ss[0].toCharArray();
            hand.cards[i] = toCard(chars[i]);
        }
        hand.value = Integer.parseInt(ss[1]);
        hand.type = toTypeWithJocker(hand.cards);
        return hand;
    }

    /**
     * Типы:
     * 5 карт, 4 карты, 2 + 3, 3, 2 + 2, 2, старшая.
     * старшая - 1,
     * 2 - 2,
     * 2+2 - 3
     * 3 - 4
     * 2+3 - 5
     * 4 - 6
     * 5 - 7
     */
    private int toType(int[] cards) {
        int[] cnt = new int[15];
        for (int card : cards) {
            cnt[card]++;
        }
        Arrays.sort(cnt);
        int max = cnt[14];
        int prev = cnt[13];

        return switch (max) {
            case 5 -> 7;
            case 4 -> 6;
            case 3 -> prev == 2 ? 5 : 4;
            case 2 -> prev == 2 ? 3 : 2;
            default -> 1;
        };
    }

    // Джокеры всегда одинаковые!
    private int toTypeWithJocker(int[] cards) {
        if (!hasJocker(cards)) {
            return toType(cards);
        }
        int bestType = toType(cards);
        for (int i = 14; i > 1; i--) {
            if (i == 11) continue;
            int[] tmpCards = Arrays.copyOf(cards, 5);
            for (int j = 0; j < 5; j++) {
                if (tmpCards[j] == 1) {
                    tmpCards[j] = i;
                }
            }
            int tmpType = toType(tmpCards);
            if (tmpType > bestType) {
                bestType = tmpType;
            }
        }
        return bestType;
    }

    private boolean hasJocker(int[] cards) {
        for (int card : cards) {
            if (card == 1) return true;
        }
        return false;
    }

    private int toCard(char c) {
        return switch (c) {
            case 'A' -> 14;
            case 'K' -> 13;
            case 'Q' -> 12;
            case 'J' -> 1;
            case 'T' -> 10;
            default -> c - '0';
        };
    }

    static class Hand implements Comparable<Hand> {
        int[] cards = new int[5];
        int value;
        int type;

        @Override
        public int compareTo(Hand h) {
            int diff = this.type - h.type;
            if (diff != 0) {
                return diff;
            }
            for (int i = 0; i < 5; i++) {
                int cardDiff = this.cards[i] - h.cards[i];
                if (cardDiff != 0) {
                    return cardDiff;
                }
            }
            return 0;
        }
    }

}
