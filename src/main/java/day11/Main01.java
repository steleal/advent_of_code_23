package day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main01 {
    public static final String START = "D:\\1\\advent_of_code\\2023_12\\src\\main\\resources\\";

    public static void main(String[] args) throws IOException {
        new Main01().run();
    }

    private void run() throws IOException {
        List<String> rows = Files.readAllLines(Paths.get(START + "day11\\input.txt"));
        char[][] map = parseMap(rows);
        List<Galaxy> galaxies = getGalaxies(map);

        Diffs diffs = getDiffs(map, 2);
        long result = getDistSums(galaxies, diffs);
        System.out.println("Part 1: " + result);

        diffs = getDiffs(map, 1000000);
        result = getDistSums(galaxies, diffs);
        System.out.println("Part 2: " + result);
    }

    private Diffs getDiffs(char[][] map, int multi) {
        int rowCnt = map.length;
        int colCnt = map[0].length;
        int[] rowDiffs = new int[rowCnt];
        int[] colDiffs = new int[colCnt];
        for (int i = 1; i < rowCnt; i++) {
            char[] row = map[i];
            boolean isGalaxy = false;
            for (int j = 0; j < colCnt; j++) {
                if (row[j] == '#') {
                    isGalaxy = true;
                    break;
                }
            }
            rowDiffs[i] = rowDiffs[i - 1] + (isGalaxy ? 1 : multi);
        }
        for (int i = 1; i < colCnt; i++) {
            boolean isGalaxy = false;
            for (int j = 0; j < rowCnt; j++) {
                if (map[j][i] == '#') {
                    isGalaxy = true;
                    break;
                }
            }
            colDiffs[i] = colDiffs[i - 1] + (isGalaxy ? 1 : multi);
        }
        return new Diffs(rowDiffs, colDiffs);
    }

    private List<Galaxy> getGalaxies(char[][] map) {
        int rowCnt = map.length;
        int colCnt = map[0].length;
        List<Galaxy> galaxies = new ArrayList<>();
        int cnt = 0;
        for (int i = 0; i < rowCnt; i++) {
            char[] row = map[i];
            for (int j = 0; j < colCnt; j++) {
                char c = row[j];
                if (c == '#') {
                    galaxies.add(new Galaxy(cnt++, i, j));
                }
            }
        }
        return galaxies;
    }

    private long getDistSums(List<Galaxy> galaxies, Diffs diffs) {
        int size = galaxies.size() - 1;
        long sum = 0;
        for (int i = 0; i < size; i++) {
            Galaxy g1 = galaxies.get(i);
            for (int j = i; j <= size; j++) {
                sum += dist(g1, galaxies.get(j), diffs);
            }
        }
        return sum;
    }

    private long dist(Galaxy g1, Galaxy g2, Diffs diffs) {
        return Math.abs(diffs.rowDiffs[g1.row] - diffs.rowDiffs[g2.row])
                + Math.abs(diffs.colDiffs[g1.col] - diffs.colDiffs[g2.col]);
    }

    private char[][] parseMap(List<String> rows) {
        char[][] map = new char[rows.size()][];
        int i = 0;
        for (String row : rows) {
            map[i++] = row.toCharArray();
        }
        return map;
    }

    static class Galaxy {
        int num;
        int row;
        int col;

        public Galaxy(int num, int row, int col) {
            this.num = num;
            this.row = row;
            this.col = col;
        }
    }

    static class Diffs {
        int[] rowDiffs;
        int[] colDiffs;

        public Diffs(int[] rowDiffs, int[] colDiffs) {
            this.rowDiffs = rowDiffs;
            this.colDiffs = colDiffs;
        }
    }
}
