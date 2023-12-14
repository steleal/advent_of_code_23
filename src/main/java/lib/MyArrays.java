package lib;

import java.util.Arrays;

public class MyArrays {

    public static char[][] toChar2dArr(String str) {
        String[] rows = str.split("\n");
        char[][] result = new char[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            result[i] = rows[i].toCharArray();
        }
        return result;
    }

    public static int[][] toInt2dArr(String str) {
        String[] rows = str.split("\n");
        int[][] result = new int[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            String[] s = rows[i].split(" ");
            result[i] = new int[s.length];
            for (int j = 0; j < s.length; j++) {
                result[i][j] = Integer.parseInt(s[i]);
            }
        }
        return result;
    }

    public static long[][] toLong2dArr(String str) {
        String[] rows = str.split("\n");
        long[][] result = new long[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            String[] s = rows[i].split(" ");
            result[i] = new long[s.length];
            for (int j = 0; j < s.length; j++) {
                result[i][j] = Long.parseLong(s[i]);
            }
        }
        return result;
    }

    public static long[][] copy(long[][] arr) {
        int rowCnt = arr.length;
        long[][] result = new long[rowCnt][];
        for (int j = 0; j < rowCnt; j++) {
            result[j] = Arrays.copyOf(arr[j], arr[j].length);
        }
        return result;
    }

    public static int[][] copy(int[][] arr) {
        int rowCnt = arr.length;
        int[][] result = new int[rowCnt][];
        for (int j = 0; j < rowCnt; j++) {
            result[j] = Arrays.copyOf(arr[j], arr[j].length);
        }
        return result;
    }

    public static char[][] copy(char[][] arr) {
        int rowCnt = arr.length;
        char[][] result = new char[rowCnt][];
        for (int j = 0; j < rowCnt; j++) {
            result[j] = Arrays.copyOf(arr[j], arr[j].length);
        }
        return result;
    }

    public static boolean deepEq(char[][] arr1, char[][] arr2) {
        for (int i = 0; i < arr1.length; i++) {
            if (!Arrays.equals(arr1[i], arr2[i])) return false;
        }
        return true;
    }

    public static boolean deepEq(int[][] arr1, int[][] arr2) {
        for (int i = 0; i < arr1.length; i++) {
            if (!Arrays.equals(arr1[i], arr2[i])) return false;
        }
        return true;
    }

    public static boolean deepEq(long[][] arr1, long[][] arr2) {
        for (int i = 0; i < arr1.length; i++) {
            if (!Arrays.equals(arr1[i], arr2[i])) return false;
        }
        return true;
    }

    public static char[][] transpose(char[][] chars) {
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

    public static int[][] transpose(int[][] ints) {
        int rowCnt = ints.length;
        int colCnt = ints[0].length;
        int[][] result = new int[colCnt][rowCnt];
        for (int i = 0; i < rowCnt; i++) {
            int[] row = ints[i];
            for (int j = 0; j < colCnt; j++) {
                result[j][i] = row[j];
            }
        }
        return result;
    }

    public static long[][] transpose(long[][] longs) {
        int rowCnt = longs.length;
        int colCnt = longs[0].length;
        long[][] result = new long[colCnt][rowCnt];
        for (int i = 0; i < rowCnt; i++) {
            long[] row = longs[i];
            for (int j = 0; j < colCnt; j++) {
                result[j][i] = row[j];
            }
        }
        return result;
    }

}
