package day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Lanternfish {

    record FishAndDay(double fish, double day) {}

    static Map<FishAndDay, Double> fishCount = new HashMap<>();

    public static void main(String[] args) throws IOException {
        Stream<String> lines = Files.lines(Paths.get("inputs/day6"));
        double fishes = lines
                .flatMap(line -> Stream.of(line.split(",")))
                .mapToDouble(Integer::parseInt)
                .reduce(0, (totalFish, currentFish) -> totalFish + 1 + spawnFish(currentFish, 256));
        lines.close();

        System.out.println(fishes);

    }


    static double spawnFish(double fish, double days) {

        FishAndDay fishAndDay = new FishAndDay(fish, days);

        if (fishCount.containsKey(fishAndDay)) {
            return fishCount.get(fishAndDay);
        }

        if (days == 0) return 0;

        double count = 0;

        if (fish < days) {
            days = days - fish - 1;
            count++;

            count += spawnFish(8, days);
            count += spawnFish(6, days);

            fishCount.put(fishAndDay, count);
            return count;
        }
        return 0;
    }
}
