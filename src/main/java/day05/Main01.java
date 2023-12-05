package day05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Main01 {
    public static final String START = "D:\\1\\advent_of_code\\2023_12\\src\\main\\resources\\";

    public static void main(String[] args) throws IOException {
        new Main01().run();
    }

    private void run() throws IOException {
        String str = Files.readString(Paths.get(START + "day05\\input.txt"));
        Almanac almanac = parseAlmanac(str);

        long result = almanac.seeds.stream()
                .mapToLong(seed -> seedToLocation(seed, almanac.rulesQueue))
                .min()
                .orElse(-1);
        System.out.println("Part 1: " + result);

        long t1 = System.currentTimeMillis();
        long result2 = pairToSeedsStream(almanac.seeds)
                .parallel()
                .map(seed -> seedToLocation(seed, almanac.rulesQueue))
                .min()
                .orElse(-1);
        System.out.println("Part 2: " + result2);
        System.out.println("Time in sec: " + (System.currentTimeMillis() - t1) / 1000);
    }

    // Во второй части seeds - это не семена, это пары - начало диапазона семян и его размер.
    // Диапазоны семян в лоб переделываем в семена, а дальше все то же самое.
    private LongStream pairToSeedsStream(List<Long> seeds) {
        long[][] arr = new long[seeds.size() / 2][2];
        for (int i = 0; i < seeds.size() / 2; i++) {
            arr[i][0] = seeds.get(i * 2);
            arr[i][1] = seeds.get(i * 2 + 1);
        }
        return Arrays.stream(arr)
                .flatMapToLong(pair -> LongStream.iterate(pair[0], i -> i + 1).limit(pair[1]));
    }

    private Almanac parseAlmanac(String str) {
        String[] strings = str.split("\n\n");
        Almanac almanac = new Almanac();
        almanac.seeds = Arrays.stream(strings[0].replace("seeds: ", "").split(" "))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        List<List<Rule>> rulesQueue = new ArrayList<>();
        for (int i = 1; i < strings.length; i++) {
            String strRules = strings[i].split(":\n")[1];
            List<Rule> rules = Arrays.stream(strRules.split("\n"))
                    .map(strRule -> {
                        String[] s = strRule.split(" ");
                        Rule rule = new Rule();
                        rule.dst = Long.parseLong(s[0]);
                        rule.src = Long.parseLong(s[1]);
                        rule.range = Long.parseLong(s[2]);
                        return rule;
                    })
                    .collect(Collectors.toList());
            rulesQueue.add(rules);
        }
        almanac.rulesQueue = rulesQueue;
        return almanac;
    }

    private Long seedToLocation(Long seed, List<List<Rule>> rulesQueue) {
        long dst = seed;
        for (List<Rule> rules : rulesQueue) {
            dst = getDstNumber(rules, dst);
        }
        return dst;
    }

    static class Almanac {
        List<Long> seeds;
        List<List<Rule>> rulesQueue;
    }

    static class Rule {
        long src;
        long dst;
        long range;
    }

    public long getDstNumber(List<Rule> rules, long src) {
        for (Rule rule : rules) {
            long diff = src - rule.src;
            if (diff >= 0 && diff < rule.range) {
                return rule.dst + diff;
            }
        }
        return src;
    }

}
