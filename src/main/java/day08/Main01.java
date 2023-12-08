package day08;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Main01 {
    public static final String START = "D:\\1\\advent_of_code\\2023_12\\src\\main\\resources\\";

    public static void main(String[] args) throws IOException {
        new Main01().run();
    }

    private void run() throws IOException {
        List<String> strings = Files.readAllLines(Paths.get(START + "day08\\input.txt"));
//        List<String> strings = getTest();
        char[] path = strings.get(0).toCharArray();
        int lastPathId = path.length - 1;
        Node node = parseNodes(strings.subList(2, strings.size()));

        int result = 0;
        int j = 0;
        while (!node.isEnd) {
            node = node.next(path[j]);
            j = j == lastPathId ? 0 : j + 1;
            result++;
        }

        System.out.println("Part 1: " + result);
    }

    private Node parseNodes(List<String> strings) {
        Map<String, Node> nameToNode = new HashMap<>();
        for (String string : strings) {
            String name = string.split(" = ")[0];
            nameToNode.put(name, new Node(name));
        }
        Node startNode = null;
        for (String string : strings) {
            String[] split = string.split(" = ");
            String name = split[0];
            String[] childNames = split[1].replace("(", "").replace(")", "").split(", ");
            Node node = nameToNode.get(name);
            Node left = nameToNode.get(childNames[0]);
            Node right = nameToNode.get(childNames[1]);
            node.left = left;
            node.right = right;
            if ("AAA".equals(name)) {
                startNode = node;
            }
        }
        return startNode;
    }

    static class Node {
        String name;
        Node left;
        Node right;
        boolean isEnd;
        public Node (String name) {
            this.name = name;
            this.isEnd = "ZZZ".equals(name);
        }
        public Node next(char c) {
            return c == 'L' ? left : right;
        }
    }

    private List<String> getTest() {
        return Arrays.asList(("""
                LLR
                                
                AAA = (BBB, BBB)
                BBB = (AAA, ZZZ)
                ZZZ = (ZZZ, ZZZ)                
                """).split("\n"));
    }

}
