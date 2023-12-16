package day16;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;

import static lib.MyArrays.toChar2dArr;

public class Main {
    public static final String START = "D:\\1\\advent_of_code\\2023_12\\src\\main\\resources\\";

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    private void run() throws IOException {
        assertThat(resolvePart1(getTest()), 46);

        String str = Files.readString(Paths.get(START + "day16\\input.txt"));

        System.out.println("Part 1: " + resolvePart1(str));

        assertThat(resolvePart2(getTest()), 51);
        System.out.println("Part 2: " + resolvePart2(str));
    }

    private String getTest() {
        return """
                .|...\\....
                |.-.\\.....
                .....|-...
                ........|.
                ..........
                .........\\
                ..../.\\\\..
                .-.-/..|..
                .|....-|.\\
                ..//.|....
                """.trim();
    }

    private long resolvePart2(String str) {
        char[][] arr = toChar2dArr(str);
        int rowCnt = arr.length;
        int colCnt = arr[0].length;
        long maxEnTiles = 0;

        Beam start;
        for (int i = 0; i < colCnt; i++) {
            start = new Beam(new Point(0, i), 2);
            maxEnTiles = Math.max(maxEnTiles, moveBeamAndReturnCntOfTiles(arr, start));
            start = new Beam(new Point(rowCnt - 1, i), 0);
            maxEnTiles = Math.max(maxEnTiles, moveBeamAndReturnCntOfTiles(arr, start));
        }

        for (int i = 0; i < rowCnt; i++) {
            start = new Beam(new Point(i, 0), 1);
            maxEnTiles = Math.max(maxEnTiles, moveBeamAndReturnCntOfTiles(arr, start));
            start = new Beam(new Point(i, colCnt - 1), 3);
            maxEnTiles = Math.max(maxEnTiles, moveBeamAndReturnCntOfTiles(arr, start));
        }
        return maxEnTiles;
    }

    private long resolvePart1(String str) {
        char[][] arr = toChar2dArr(str);
        Beam start = new Beam(new Point(0, 0), 1);
        return moveBeamAndReturnCntOfTiles(arr, start);
    }

    private long moveBeamAndReturnCntOfTiles(char[][] arr, Beam start) {
        // beams direction: NESW - 0, 1, 2, 3.
        boolean[][][] visitDirections = new boolean[arr.length][arr[0].length][4];

        Queue<Beam> beams = new LinkedList<>();
        beams.add(start);
        while (beams.size() > 0) {
            Beam beam = beams.poll();
            Point point = beam.point;
            if (!isPointIn(arr, point)) continue;
            boolean[] visitDir = visitDirections[point.row][point.col];
            if (visitDir[beam.direction]) continue;
            visitDir[beam.direction] = true;

            char c = arr[point.row][point.col];
            if (c == '.'
                    || (c == '-' && (beam.direction % 2 == 1))
                    || (c == '|' && (beam.direction % 2 == 0))
            ) {
                beams.add(new Beam(beam.nextPoint(), beam.direction));
            } else if (c == '\\' || c == '/') {
                int nextDir = getNewDir(beam.direction, c);
                beams.add(new Beam(point.nextPoint(nextDir), nextDir));
            } else {
                int left = getLeft(beam.direction);
                beams.add(new Beam(point.nextPoint(left), left));
                int right = getRight(beam.direction);
                beams.add(new Beam(point.nextPoint(right), right));
            }
        }

        return calcEnergizedTiles(arr, visitDirections);
    }

    private int getRight(int direction) {
        return (direction + 1) & 3;
    }

    private int getLeft(int direction) {
        return (direction + 3) & 3;
    }

    private int getNewDir(int direction, char c) {
        if (c == '\\') {
            // 0 - 3, 1-2, 2 - 1, 3 - 0;
            return switch (direction) {
                case 0 -> 3;
                case 1 -> 2;
                case 2 -> 1;
                default -> 0;
            };
        } else {
            // 0 - 1, 1 - 0, 2 - 3, 3 - 2
            return switch (direction) {
                case 0 -> 1;
                case 1 -> 0;
                case 2 -> 3;
                default -> 2;
            };
        }
    }

    private boolean isPointIn(char[][] arr, Point point) {
        int rowCnt = arr.length;
        int colCnt = arr[0].length;
        int row = point.row;
        int col = point.col;
        return row >= 0 && row < rowCnt && col >= 0 && col < colCnt;
    }

    private long calcEnergizedTiles(char[][] arr, boolean[][][] visited) {
        int rowCnt = arr.length;
        int colCnt = arr[0].length;
        int sum = 0;
        for (int i = 0; i < rowCnt; i++) {
            for (int j = 0; j < colCnt; j++) {
                boolean[] isVisit = visited[i][j];
                sum += (isVisit[0] || isVisit[1] || isVisit[2] || isVisit[3])
                        ? 1 : 0;
            }
        }
        return sum;
    }

    private void assertThat(long actual, long expected) {
        if (actual != expected) {
            throw new RuntimeException("Fall test");
        }
    }

    static class Beam {
        Point point;
        int direction;

        public Beam(Point point, int direction) {
            this.point = point;
            this.direction = direction;
        }

        public Point nextPoint() {
            return point.nextPoint(direction);
        }
    }

    static class Point {
        private static final int[] diffR = new int[]{-1, 0, 1, 0};
        private static final int[] diffC = new int[]{0, 1, 0, -1};

        int row;
        int col;

        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public Point nextPoint(int dir) {
            return new Point(row + diffR[dir], col + diffC[dir]);
        }
    }
}
