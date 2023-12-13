package day13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static final String START = "D:\\1\\advent_of_code\\2023_12\\src\\main\\resources\\";

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    private void run() throws IOException {
        String str = Files.readString(Paths.get(START + "day13\\input.txt"));
        List<Note> notes = Arrays.stream(str.split("\n\n"))
                .map(this::toNote).collect(Collectors.toList());

        long result = notes.stream()
                .mapToLong(this::findReflection1)
                .sum();
        System.out.println("Part 1: " + result);

        result = notes.stream()
                .mapToLong(this::findReflection2)
                .sum();
        System.out.println("Part 2: " + result);
    }

    private long findReflection1(Note n) {
        return  100 * calcReflection(n.inRows, 0) + calcReflection(n.inCols, 0);
    }

    private long findReflection2(Note n) {
        int lr = (int) calcReflection(n.inRows, 0);
        int lc = (int) calcReflection(n.inCols, 0);
        long l = lr * 100L + lc;
        int cnt = n.inRows.length * n.inRows[0].length;
        for (int i = 0; i < cnt; i++) {
            Note mut = new Note(mutate(n.inRows, i));
            long t = 100 * calcReflection(mut.inRows, lr) + calcReflection(mut.inCols, lc);
            if (t > 0 && t != l) {
                return t;
            }
        }
        return l;
    }

    private char[][] mutate(char[][] chars, int i) {
        int rowCnt = chars.length;
        int colCnt = chars[0].length;
        // full copy array
        char[][] result = new char[rowCnt][];
        for (int j = 0; j < rowCnt; j++) {
            result[j] = chars[j].clone();
        }
        // replace a cell
        int row = i / colCnt;
        int col = i % colCnt;
        char c = chars[row][col];
        result[row][col] = c == '#' ? '.' : '#';
        return result;
    }

    private long calcReflection(char[][] rows, int skip) {
        int rowCnt = rows.length;
        for (int i = 0; i < rowCnt - 1; i++) {
            if (i + 1 == skip) continue;
            int diff = Math.min(i + 1, rowCnt - i - 1);
            boolean isReflection = true;
            for (int j = 0; j < diff; j++) {
                if (!Arrays.equals(rows[i - j], rows[i + 1 + j])) {
                    isReflection = false;
                    break;
                }
            }
            if (isReflection) {
                return i + 1;
            }
        }
        return 0;
    }

    private Note toNote(String s) {
        String[] rows = s.split("\n");
        char[][] chars = new char[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            chars[i] = rows[i].toCharArray();
        }
        return new Note(chars);
    }

    static class Note {
        char[][] inRows;
        char[][] inCols;

        public Note(char[][] inRows) {
            this.inRows = inRows;
            this.inCols = transpose(inRows);
        }

        private char[][] transpose(char[][] chars) {
            int rowCnt = chars.length;
            int colCnt = chars[0].length;
            char[][] result = new char[colCnt][rowCnt];
            for (int i = 0; i < rowCnt; i++) {
                char[] row = chars[i];
                for (int j = 0; j < colCnt; j++) {
                    result[j][i] = row[j];
                }
            }
            return result;
        }
    }
}
