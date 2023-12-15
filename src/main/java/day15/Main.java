package day15;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    public static final String START = "D:\\1\\advent_of_code\\2023_12\\src\\main\\resources\\";

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    private void run() throws IOException {
        assertThat(resolvePart1(getTest()), 1320, "Fall part 1");

        String str = Files.readString(Paths.get(START + "day15\\input.txt"));

        System.out.println("Part 1: " + resolvePart1(str));

        assertThat(resolvePart2(getTest().trim()), 145, "Fall part 2");
        System.out.println("Part 2: " + resolvePart2(str.trim()));
    }

    private String getTest() {
        return """
                rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7
                """;
    }

    private long resolvePart2(String str) {
        final Map<String, Integer>[] boxMap = initBoxes();
        for (String s : str.split(",")) {
            String[] ss = s.split("[=\\-]");
            boolean isDash = ss.length == 1;
            String label = ss[0];
            int boxNum = myHash(label);
            if (isDash) {
                boxMap[boxNum].remove(label);
            } else {
                boxMap[boxNum].put(label, Integer.parseInt(ss[1]));
            }
            System.out.println();
        }

        return calcFocusingPower(boxMap);
    }

    private Map<String, Integer>[] initBoxes() {
        final Map<String, Integer>[] boxMap = new Map[256];
        for (int i = 0; i < 256; i++) {
            boxMap[i] = new LinkedHashMap<>();
        }
        return boxMap;
    }

    private long calcFocusingPower(Map<String, Integer>[] boxes) {
        int sum = 0;
        for (int i = 0; i < boxes.length; i++) {
            Map<String, Integer> map = boxes[i];
            if (map.isEmpty()) continue;
            int boxNum = i + 1;
            int slot = 0;
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                slot++;
                sum += boxNum * slot * entry.getValue();
            }
        }
        return sum;
    }

    private long resolvePart1(String str) {
        return Arrays.stream(str.split(","))
                .mapToInt(this::myHash)
                .sum();
    }

    private int myHash(String s) {
        int result = 0;
        for (char c : s.toCharArray()) {
            if (c == '\n') continue;
            result += c;
            result = (result * 17) & 255;
        }
        return result;
    }

    private void assertThat(long actual, long expected, String msg) {
        if (actual != expected) throw new RuntimeException(msg);
    }

}
