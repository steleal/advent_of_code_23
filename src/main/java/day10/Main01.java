package day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main01 {
    public static final String START = "D:\\1\\advent_of_code\\2023_12\\src\\main\\resources\\";

    public static void main(String[] args) throws IOException {
        new Main01().run();
    }

    private void run() throws IOException {
        List<String> rows = Files.readAllLines(Paths.get(START + "day10\\input.txt"));
        char[][] maze = parseMaze(rows);
        Point start = findPoint(maze, 'S');
        int result = moveByPypeMaze(maze, start);
        System.out.println("Part 1: " + result);
    }

    private int moveByPypeMaze(char[][] maze, Point start) {
        int rowsCnt = maze.length;
        int colsCnt = maze[0].length;

        boolean[][] visit = new boolean[rowsCnt][colsCnt];
        Queue<Point> queue = new LinkedList<>();
        queue.add(start);

        int cnt = 0;
        while (!queue.isEmpty()) {
            Point cell = queue.poll();
            visit[cell.row][cell.col] = true;
            cnt++;
            getNeighbors(maze, cell).forEach(n -> {
                if (!visit[n.row][n.col]) {
                    queue.add(n);
                }
            });
        }

        return cnt / 2;
    }

    private List<Point> getNeighbors(char[][] maze, Point cell) {
        List<Point> result = new ArrayList<>(4);
        Point tmp = null;
        if (cell.value == 'S') {
            // N : |,  7, F
            tmp = getCell(maze, cell.row - 1, cell.col, "|7F");
            if (tmp != null) result.add(tmp);
            // S : |, J, L
            tmp = getCell(maze, cell.row + 1, cell.col, "|JL");
            if (tmp != null) result.add(tmp);
            // W : -, L, F
            tmp = getCell(maze, cell.row, cell.col - 1, "-LF");
            if (tmp != null) result.add(tmp);
            // E : -, J, 7
            tmp = getCell(maze, cell.row, cell.col + 1, "-J7");
            if (tmp != null) result.add(tmp);
            return result;
        }

        // add N neighbor
        if (isIn("|JL", cell.value)) {
            int r = cell.row - 1, c = cell.col;
            result.add(new Point(maze[r][c], r, c));
        }
        // add S neighbor
        if (isIn("|7F", cell.value)) {
            int r = cell.row + 1, c = cell.col;
            result.add(new Point(maze[r][c], r, c));
        }
        // add W neighbor
        if (isIn("-J7", cell.value)) {
            int r = cell.row, c = cell.col - 1;
            result.add(new Point(maze[r][c], r, c));
        }
        // add W neighbor
        if (isIn("-LF", cell.value)) {
            int r = cell.row, c = cell.col + 1;
            result.add(new Point(maze[r][c], r, c));
        }
        return result;
    }

    private Point getCell(char[][] maze, int row, int col, String s) {
        int rowCnt = maze.length;
        int colCnt = maze[0].length;
        if (row < 0 || row == rowCnt || col < 0 || col == colCnt) {
            return null;
        }
        char value = maze[row][col];
        if (isIn(s, value)) {
            return new Point(value, row, col);
        }
        return null;
    }

    private boolean isIn(String s, char value) {
        for (char c : s.toCharArray()) {
            if (value == c) return true;
        }
        return false;
    }

    private Point findPoint(char[][] maze, char s) {
        int rowsCnt = maze.length;
        int colsCnt = maze[0].length;
        for (int i = 0; i < rowsCnt; i++) {
            for (int j = 0; j < colsCnt; j++) {
                if (maze[i][j] == s) {
                    return new Point(s, i, j);
                }
            }
        }
        return null;
    }

    private char[][] parseMaze(List<String> rows) {
        char[][] maze = new char[rows.size()][];
        int i = 0;
        for (String row : rows) {
            maze[i++] = row.toCharArray();
        }
        return maze;
    }

    class Point {
        char value;
        int row;
        int col;

        public Point(char value, int row, int col) {
            this.value = value;
            this.row = row;
            this.col = col;
        }
    }

}
