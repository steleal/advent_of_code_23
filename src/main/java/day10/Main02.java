package day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main02 {
    public static final String START = "D:\\1\\advent_of_code\\2023_12\\src\\main\\resources\\";

    public static void main(String[] args) throws IOException {
        new Main02().run();
    }

    private void run() throws IOException {
        List<String> rows = Files.readAllLines(Paths.get(START + "day10\\input.txt"));
        char[][] maze = parseMazeAsDoubleMazeWithEdge(rows);
        boolean[][] visit = new boolean[maze.length][maze[0].length];
        Point start = findStartPoint(maze);

        moveByPypeInMaze(maze, visit, start);
        fillOutsideTilesByO(maze, visit);
        int result = countEnclosedTiles(maze, visit);

        System.out.println("Part 2: " + result);
    }

    private int countEnclosedTiles(char[][] maze, boolean[][] visit) {
        int cnt = 0;
        for (int i = 2; i < maze.length; i += 2) {
            for (int j = 2; j < maze[0].length; j += 2) {
                if (!visit[i][j] && maze[i][j] != 'O') {
                    cnt++;
                }
            }
        }
        return cnt;
    }

    private void fillOutsideTilesByO(char[][] maze, boolean[][] visit) {
        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point('O', 0, 0));
        int rowCnt = maze.length;
        int colCnt = maze[0].length;
        int[] rd = new int[]{0, 1, 0, -1};
        int[] cd = new int[]{1, 0, -1, 0};

        while (!queue.isEmpty()) {
            Point cell = queue.poll();
            if (maze[cell.row][cell.col] == 'O') continue;
            maze[cell.row][cell.col] = 'O';

            for (int i = 0; i < 4; i++) {
                int r = cell.row + rd[i];
                int c = cell.col + cd[i];
                if (r < 0 || r == rowCnt || c < 0 || c == colCnt
                        || visit[r][c]
                        || maze[r][c] == 'O') {
                    continue;
                }
                queue.add(new Point('O', r, c));
            }
        }
    }

    private void moveByPypeInMaze(char[][] maze, boolean[][] visit, Point start) {
        Queue<Point> queue = new LinkedList<>();
        queue.add(start);
        while (!queue.isEmpty()) {
            Point cell = queue.poll();
            visit[cell.row][cell.col] = true;
            getNeighborsInPipe(maze, cell).forEach(n -> {
                if (!visit[n.row][n.col]) {
                    queue.add(n);
                }
            });
        }
    }

    private List<Point> getNeighborsInPipe(char[][] maze, Point cell) {
        if (cell.value == 'S') {
            return getNeighborsForS(maze, cell);
        }

        List<Point> result = new ArrayList<>(4);
        // add North neighbor
        if (isIn(cell.value, '|', 'J', 'L')) {
            int r = cell.row - 1, c = cell.col;
            result.add(new Point(maze[r][c], r, c));
        }
        // add South neighbor
        if (isIn(cell.value, '|', '7', 'F')) {
            int r = cell.row + 1, c = cell.col;
            result.add(new Point(maze[r][c], r, c));
        }
        // add West neighbor
        if (isIn(cell.value, '-', 'J', '7')) {
            int r = cell.row, c = cell.col - 1;
            result.add(new Point(maze[r][c], r, c));
        }
        // add East neighbor
        if (isIn(cell.value, '-', 'L', 'F')) {
            int r = cell.row, c = cell.col + 1;
            result.add(new Point(maze[r][c], r, c));
        }
        return result;
    }

    private List<Point> getNeighborsForS(char[][] maze, Point cell) {
        // N, S, W, E
        int[] rd = new int[]{-1, 1, 0, 0};
        int[] cd = new int[]{0, 0, -1, 1};
        String[] sd = new String[]{"|7F", "|JL", "-LF", "-J7"};

        List<Point> result = new ArrayList<>(4);
        Point tmp;
        for (int i = 0; i < 4; i++) {
            tmp = getNeighborForS(maze, cell.row + rd[i], cell.col + cd[i], sd[i]);
            if (tmp != null) result.add(tmp);
        }
        return result;
    }

    private Point getNeighborForS(char[][] maze, int row, int col, String charRange) {
        int rowCnt = maze.length;
        int colCnt = maze[0].length;
        if (row < 0 || row == rowCnt || col < 0 || col == colCnt) {
            return null;
        }
        char value = maze[row][col];
        if (isIn(value, charRange.toCharArray())) {
            return new Point(value, row, col);
        }
        return null;
    }

    private boolean isIn(char value, char... arr) {
        for (char c : arr) {
            if (value == c) return true;
        }
        return false;
    }

    private Point findStartPoint(char[][] maze) {
        int rowsCnt = maze.length;
        int colsCnt = maze[0].length;
        for (int i = 0; i < rowsCnt; i++) {
            for (int j = 0; j < colsCnt; j++) {
                if (maze[i][j] == 'S') {
                    return new Point('S', i, j);
                }
            }
        }
        return null;
    }

    /**
     * S7 -> ......
     * LJ    ......
     *       ..S-7.
     *       ..|.|.
     *       ..L-J.
     *       ......
     */
    private char[][] parseMazeAsDoubleMazeWithEdge(List<String> rows) {
        char[][] maze = parseOriginMaze(rows);
        char[][] doubleMaze = new char[maze.length * 2 + 2][maze[0].length * 2 + 2];
        for (int i = 0; i < maze.length; i++) {
            char[] row = maze[i];
            int i1 = i * 2 + 2;
            int i2 = i1 + 1;
            for (int j = 0; j < row.length; j++) {
                int j1 = j * 2 + 2;
                int j2 = j1 + 1;
                char cell = row[j];
                doubleMaze[i1][j1] = cell;
                doubleMaze[i1][j2] = switch (cell) {
                    case '-', 'L', 'F' -> '-';
                    case 'S' -> (j != row.length - 1 && isIn(row[j + 1], '-', 'J', '7')) ? '-' : '.';
                    default -> '.';
                };
                doubleMaze[i2][j1] = switch (cell) {
                    case '|', '7', 'F' -> '|';
                    case 'S' -> (i != maze.length - 1 && isIn(maze[i + 1][j], '|', 'J', 'L')) ? '|' : '.';
                    default -> '.';
                };
                doubleMaze[i2][j2] = '.';
            }
        }
        return doubleMaze;
    }

    private char[][] parseOriginMaze(List<String> rows) {
        char[][] maze = new char[rows.size()][];
        int i = 0;
        for (String row : rows) {
            maze[i++] = row.toCharArray();
        }
        return maze;
    }

    static class Point {
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
