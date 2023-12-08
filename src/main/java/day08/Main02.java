package day08;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main02 {
    public static final String START = "D:\\1\\advent_of_code\\2023_12\\src\\main\\resources\\";

    public static void main(String[] args) throws IOException {
        new Main02().run();
    }

    private void run() throws IOException {
        List<String> strings = Files.readAllLines(Paths.get(START + "day08\\input.txt"));
        char[] path = strings.get(0).toCharArray();
        int lastPathId = path.length - 1;
        Node[] nodes = parseNodes(strings.subList(2, strings.size())).toArray(new Node[0]);
        /*
        В лоб за 15 минут не посчиталось, пришлось думать.

        Нод всего 714 (вроде бы), стартовых нод - 6, путь из карты - 283 шага.
        Рано или поздно после применения полного пути ноды должны будут повторяться, т.е. путь зациклится.
        Находим период повторения нод после применения полного пути для каждой стартовой ноды
        (стартовая кстати не повторяется, но следующая за ней).
        Обращаем внимание, что:
        1) последняя нода в периоде заканчивается на Z. Т.е. после каждых P * 283 шагов будем иметь финишную ноду.
        2) период повторения - простое число, т.е. периоды не кратны друг другу.
        Следовательно, все пути будут заканчиваться на Z через P1 * P2 * P3 * P4 * P5 * P6 * 283 шагов.
         */
        long[] cycleLen = new long[nodes.length];

        for (int i = 0; i < nodes.length; i++) {
            Node node = nodes[i];
            Set<Node> set = new HashSet<>();
            int j = 0;
            while (true) {
                node = node.next(path[j]);
                j = j == lastPathId ? 0 : j + 1;
                if (j == 0) {
                    if (set.contains(node)) break;
                    set.add(node);
                }
            }
            cycleLen[i] = set.size();
        }

        long result = Arrays.stream(cycleLen).reduce((l, r) -> l * r).orElse(0) * 283;

        System.out.println("Part 2: " + result);
    }

    private List<Node> parseNodes(List<String> strings) {
        Map<String, Node> nameToNode = new HashMap<>();
        for (String string : strings) {
            String name = string.split(" = ")[0];
            nameToNode.put(name, new Node(name));
        }
        List<Node> startNodes = new ArrayList<>();
        for (String string : strings) {
            String[] split = string.split(" = ");
            String name = split[0];
            String[] childNames = split[1].replace("(", "").replace(")", "").split(", ");
            Node node = nameToNode.get(name);
            Node left = nameToNode.get(childNames[0]);
            Node right = nameToNode.get(childNames[1]);
            node.left = left;
            node.right = right;
            if (name.endsWith("A")) {
                startNodes.add(node);
            }
        }
        return startNodes;
    }

    static class Node {
        String name;
        Node left;
        Node right;
        boolean isEnd;

        public Node(String name) {
            this.name = name;
            this.isEnd = name.endsWith("Z");
        }

        public Node next(char c) {
            return c == 'L' ? left : right;
        }
    }

}
