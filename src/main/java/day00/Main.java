package day00;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static lib.MyArrays.copy;
import static lib.MyArrays.toChar2dArr;
import static lib.MyArrays.transpose;

public class Main {
    public static final String START = "D:\\1\\advent_of_code\\2023_12\\src\\main\\resources\\";

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    private void run() throws IOException {
        assertThat(resolvePart1(getTest()), 111);

        String str = Files.readString(Paths.get(START + "day00\\input.txt"));

        System.out.println("Part 1: " + resolvePart1(str));

//        assertThat(resolvePart2(getTest()), 222);
//        System.out.println("Part 2: " + resolvePart2(str));
    }

    private String getTest() {
        return """
                                
                """;
    }

    private long resolvePart1(String str) {
//        return Arrays.stream(str.split("\n"))
//                .mapToLong(Long::parseLong)
//                .sum();

//        return Arrays.stream(str.split("\n\n"))
//                .map(this::toObj)
//                .peek(e -> System.out.println("Obj:\n" + e))
//                .mapToLong(this::calcForObj)
//                .sum();

        char[][] arr = toChar2dArr(str);
        char[][] copy = copy(arr);
        transpose(copy);

        return 111;
    }

    private long resolvePart2(String str) {

        return 222;
    }

    private void assertThat(long actual, long expected) {
        if (actual != expected) throw new RuntimeException("Fall test");
    }

}
