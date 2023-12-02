package day02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Main02 {
    public static final String START = "D:\\1\\advent_of_code\\2023_12\\src\\main\\resources\\";

    public static void main(String[] args) throws IOException {
        new Main02().run();
    }

    private void run() throws IOException {
        List<String> strings = null;
        strings = Files.readAllLines(Paths.get(START + "day02\\input.txt"));
//        strings = getTest();

        int result = strings.stream()
                .mapToInt(this::toGamePower)
                .sum();
        System.out.println(result);
    }

    private int toGamePower(String str) {
        if (str.length() < 7) return 0;
        int colonIndex = str.indexOf(":");
//        int num = Integer.parseInt(str.substring(5, colonIndex));
        String[] parts = str.substring(colonIndex + 2).replace(";", ",").split(", ");
        int maxRed = 0, maxGreen = 0, maxBlue = 0;
        for (String part : parts) {
            String[] s = part.split(" ");
            int cnt = Integer.parseInt(s[0]);
            String color = s[1];
            switch (color) {
                case "red" -> maxRed = Math.max(maxRed, cnt);
                case "green" -> maxGreen = Math.max(maxGreen, cnt);
                case "blue" -> maxBlue = Math.max(maxBlue, cnt);
            }
        }
        return maxRed * maxGreen * maxBlue;
    }

    private List<String> getTest() {
        String str = """
                Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
                Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
                Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
                Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
                Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
                """;
        return Arrays.asList(str.split("\n"));
    }
}
