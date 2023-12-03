import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class aoc03 {
    static List<String> data;
    static Map<String, List<Integer>> gears;
    static String gearAdj;
    public static void main(String[] args) throws IOException {

        // ¯\_(ツ)_/¯

        data = Files.readAllLines(Path.of("src/aoc03.txt"));
        //data = List.of("467..114..", "...*.....+", "..35..633.", "......#...", "617*......",
        //        ".....+.58.", "..592.....", "......755.", "...$.*....", ".664.598..");
        gears = new HashMap<>();

        int sum1 = 0;
        int sum2 = 0;
        int n = 0;
        gearAdj = "";
        boolean valid = false;
        for (int i = 0; i < data.size(); i++) {
            var line = data.get(i).toCharArray();
            for (int j = 0; j < line.length; j++) {
                if (Character.isDigit(line[j])) {
                    n = n * 10 + Character.getNumericValue(line[j]);
                    checkGear(i, j);
                    if (!valid)
                        valid = checkAdjacent(i, j);
                }
                else if (n != 0) {
                    if (!gearAdj.isEmpty()) {
                        gears.get(gearAdj).add(n);
                        gearAdj = "";
                    }
                    if (valid) {
                        sum1 += n;
                        valid = false;
                    }
                    n = 0;
                }
            }
            if (n != 0 && !gearAdj.isEmpty()) {
                gears.get(gearAdj).add(n);
                gearAdj = "";
            }
            if (n != 0 && valid) {
                sum1 += n;
                valid = false;
                n = 0;
            }
        }

        for (List<Integer> v : gears.values()) {
            if (v.size() == 2)
                sum2 += v.get(0) * v.get(1);
        }
        System.out.printf("first star: %d\nsecond star: %d%n", sum1, sum2);
    }

    public static boolean valid(int i, int j, int s, int s2) {
        return i >= 0 && i < s && j >= 0 && j < s2;
    }
    public static boolean checkAdjacent(int i, int j) {
        int s = data.size();
        int s2 = data.get(0).length();
        var indices = new int[][]{{-1,-1}, {-1,0}, {-1,1}, {0,1}, {1,1}, {1,0}, {1,-1}, {0,-1}};
        for (int[] x : indices) {
            if (valid(i + x[0], j + x[1], s, s2)){
                var c = data.get(i + x[0]).toCharArray()[j + x[1]];
                if (!Character.isDigit(c) && c != '.')
                    return true;
            }
        }
        return false;
    }
    public static void checkGear(int i, int j) {
        int s = data.size();
        int s2 = data.get(0).length();
        var indices = new int[][]{{-1,-1}, {-1,0}, {-1,1}, {0,1}, {1,1}, {1,0}, {1,-1}, {0,-1}};
        for (int[] x : indices) {
            if (valid(i + x[0], j + x[1], s, s2)){
                var c = data.get(i + x[0]).toCharArray()[j + x[1]];
                if (c == '*') {
                    gearAdj = String.format("%d,%d", i + x[0], j + x[1]);
                    if (!gears.containsKey(String.format("%d,%d", i + x[0], j + x[1])))
                        gears.put(String.format("%d,%d", i + x[0], j + x[1]), new ArrayList<>());
                }
            }
        }
    }
}
