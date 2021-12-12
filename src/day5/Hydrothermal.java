package day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Hydrothermal {

    public static void main(String[] args) throws IOException {
        Stream<String> input = Files.lines(Paths.get("inputs/day5"));

        List<Line> lines = input
                .map(line -> line.split(" -> "))
                .map(Line::new)
                .collect(Collectors.toList());

        input.close();

        Grid grid = populateGrid(lines);

        int count = 0;
        for (Map.Entry<Integer, Column> entry: grid.grid.entrySet()) {
            // System.out.print("row " + entry.getKey() + " : ");

            for (Map.Entry<Integer, Integer> column: entry.getValue().column.entrySet()) {
                if (column.getValue() >= 2) {
                    count++;
                }
                //System.out.print(column.getValue() + " ");
            }
            //System.out.println();
        }
        System.out.println(count);
    }

    private static Grid populateGrid(List<Line> lines) {
        Grid grid = new Grid();
        lines.stream()
                .flatMap(line -> line.getPoints().stream())
                .forEach(point -> {
                    Column col = grid.grid.computeIfAbsent(point.y, y -> {
                        return new Column();
                    });
                    int value = col.column.computeIfAbsent(point.x, x -> 0);
                    col.column.put(point.x, ++value);
                    // System.out.println("x : " + point.x + "y : " + point.y);
                    //System.out.println(value);
                });
        return grid;
    }

}

class Grid {
    Map<Integer, Column> grid = new HashMap<>();
}
class Column {
    Map<Integer, Integer> column = new HashMap<>();
}

class Point {
    int x;
    int y;

    Point(String s) {
        String[] nums = s.split(",");
        x = Integer.parseInt(nums[0]);
        y = Integer.parseInt(nums[1]);
    }

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + String.valueOf(x) + "," + String.valueOf(y) + ")";
    }
}

class Line {
    Point p1;
    Point p2;

    Line(String[] arr) {
        p1 = new Point(arr[0]);
        p2 = new Point(arr[1]);
    }

    boolean isHorizontal() {
        return p1.y == p2.y;
    }

    List<Point> getPoints() {
        List<Point> points = new ArrayList<>();


        if (isHorizontal()) {

            points.add(p1);
            points.add(p2);

            IntStream.range(Integer.min(p1.x, p2.x) + 1, Integer.max(p1.x, p2.x))
                    .forEach(num -> points.add(new Point(num, p1.y)));
        } else if (isVertical()) {

            points.add(p1);
            points.add(p2);

            IntStream.range(Integer.min(p1.y, p2.y) + 1, Integer.max(p1.y, p2.y))
                    .forEach(num -> points.add(new Point(p1.x, num)));
        } else {
            boolean isYless = Integer.compare(p1.y, p2.y) < 0;
            boolean isXless = Integer.compare(p1.x, p2.x) < 0;
            int x = p1.x;
            int y = p1.y;

            points.add(new Point(x, y));
            while (x != p2.x && y != p2.y) {
                x += isXless ? 1 : -1;
                y += isYless ? 1 : -1;
                points.add(new Point(x, y));
            }
//            if (isYless) {
//                points.add(p1);
//            } else {
//                points.add(p2);
//            }
//            IntStream.range(Integer.min(p1.x, p2.x), Integer.max(p1.x, p2.x) + 1)
//                    .forEach(x -> {
//                        if (isYless) {
//                            points.add(new Point(x, points.get(points.size() - 1).y + 1));
//                        } else {
//                            points.add(new Point(x, points.get(points.size() - 1).y - 1));
//                        }
//                    });
//            if (isYless) {
//                points.add(p2);
//            } else {
//                points.add(p1);
//            }
        }
        //System.out.println(points);
        return points;
    }

    private boolean isVertical() {
        return p1.x == p2.x;
    }
}