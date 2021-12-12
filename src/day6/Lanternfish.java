package day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Lanternfish {

    public static void main(String[] args) throws IOException {
        Stream<String> lines = Files.lines(Paths.get("inputs/6_example"));
        List<Integer> fishes = lines
                .flatMap(line -> Stream.of(line.split(",")))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        lines.close();

        for (int i = 1; i <= 18; i++) {

        }
    }
}
