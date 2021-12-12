package day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MinFuel {

    public static void main(String[] args) throws IOException {

        Stream<String> lines = Files.lines(Paths.get("inputs/day7"));

        List<Integer> crabs = lines
                .flatMap(line -> Stream.of(line.split(",")))
                .map(Integer::parseInt)
                .sorted()
                .collect(Collectors.toList());

        lines.close();

        //int median = crabs.get((crabs.size() / 2) - 1);

        int mean = crabs.stream().mapToInt(Integer::intValue).sum() / crabs.size();

        System.out.println(mean);
        int result1 = fuelConsumption(crabs, mean);
        int result2 = fuelConsumption(crabs, mean + 1);
        int result3 = fuelConsumption(crabs, mean - 1);

        int result = 0;

        if (result1 < result2) {
            result = Math.min(result1, result3);
        } else if (result2 < result3){
            result = result2;
        }

        System.out.println(result);

    }

    private static Integer fuelConsumption(List<Integer> crabs, int mean) {
        return crabs.stream().reduce(0, (totalFuel, currentCrab) -> {
            int difference =  Math.abs(currentCrab - mean);
            return ((difference * (difference + 1)) / 2) + totalFuel;
        });
    }
}
