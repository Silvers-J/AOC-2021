package day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Diagnostic {

    static BiFunction<List<String>, List<String>, List<String>> oxygenPredicate = (list1, list2) -> list1.size() > list2.size() ? list1 : list2;
    static BiFunction<List<String>, List<String>, List<String>> co2Predicate = (list1, list2) -> list1.size() < list2.size() ? list1 : list2;

    public static void main(String[] args) throws IOException {

        Stream<String> lines = Files.lines(Paths.get("part1"));
        List<String> list = lines.collect(Collectors.toList());
        int i = oxygenRating(list, 0);

        int i1 = co2Rating(list, 0);
        System.out.println(i  + " " + i1 + " " + i * i1);
//        LinkedList<Integer> list = (LinkedList<Integer>) lines.map(line -> Integer.parseInt(line, 2)).collect(Collectors.toList());
//        System.out.println(function(16, null, list, oxygenPredicate) *
//                function(16, null, list, co2Predicate));
//        Map<Integer, Rate> map = new HashMap<>();
//        map.put(0, new Rate());
//        map.put(1, new Rate());
//        map.put(2, new Rate());
//        map.put(3, new Rate());
//        map.put(4, new Rate());
//        map.put(5, new Rate());
//        map.put(6, new Rate());
//        map.put(7, new Rate());
//        map.put(8, new Rate());
//        map.put(9, new Rate());
//        map.put(10, new Rate());
//        map.put(11, new Rate());

//        lines.forEach(line -> {
//            for (int i = 0; i < 12; i++) {
//                if ('1' == line.charAt(i)) {
//                    map.get(i).ones++;
//                } else {
//                    map.get(i).zeros++;
//                }
//            }
//        });
//        lines.close();
//        StringBuilder gammaBuilder = new StringBuilder("");
//        StringBuilder epsilonBuilder = new StringBuilder("");
//
//        for (int i = 0; i < 12; i++) {
//            if (map.get(i).ones > map.get(i).zeros) {
//                gammaBuilder.append(1);
//                epsilonBuilder.append(0);
//            } else {
//                gammaBuilder.append(0);
//                epsilonBuilder.append(1);
//            }
//        }
//        System.out.println(gammaBuilder.toString() + " " + epsilonBuilder.toString());
//        System.out.println(Integer.parseInt(gammaBuilder.toString(), 2) * Integer.parseInt(epsilonBuilder.toString(), 2));

        int oxygenRating = findRating(list, "Oxygen");
        int CO2Rating = findRating(list, "CO2");
        System.out.println(oxygenRating + " " + CO2Rating + " " + oxygenRating * CO2Rating);
    }

    static int findRating(List<String> codes, String gasName) {

        String ratingCode = null;
        for (int i = 0; i < codes.get(0).length(); ++i) {
            int ones = 0;
            for (String code : codes) {
                if (code.charAt(i) == '1') {
                    ++ones;
                }
            }

            int zeros = codes.size() - ones;
            List<String> newCodes = new ArrayList<String>();
            for (String code : codes) {
                if (ones >= zeros) {
                    if ((gasName.equals("Oxygen") && code.charAt(i) == '1') ||
                            (gasName.equals("CO2")    && code.charAt(i) == '0')) {
                        newCodes.add(code);
                    }
                } else {
                    if ((gasName.equals("Oxygen") && code.charAt(i) == '0') ||
                            (gasName.equals("CO2")    && code.charAt(i) == '1')) {
                        newCodes.add(code);
                    }
                }
            }

            codes = newCodes;
            if (codes.size() == 1) {
                ratingCode = codes.get(0);
                break;
            }
        }

        return Integer.parseInt(ratingCode, 2);
    }
//    static int function(int divideBy, List<Integer> indexes, LinkedList<Integer> list, BiFunction<List<Integer>, List<Integer>, List<Integer>> predicate) {
//
//        System.out.println(indexes);
//        System.out.println(divideBy);
//        if (divideBy == 1) {
//            int num1 = list.get(indexes.get(0));
//
//            if (num1 % 2 == 1) return num1;
//            else return list.get(indexes.get(1));
//        }
//        List<Integer> onesIndex = new ArrayList<>();
//        List<Integer> zeroesIndex = new ArrayList<>();
//
//        if (indexes == null) {
//            IntStream.range(0, list.size()).forEach(number -> {
////                System.out.println(list.get(number) + " " + list.get(number) / divideBy);
//                if (list.get(number) / divideBy >= 1) {
//                    onesIndex.add(number);
//                    int rem = list.remove(number);
//                    list.add(number, rem );
//                } else {
//                    zeroesIndex.add(number);
//                }
//            });
////            System.out.println(onesIndex);
////            System.out.println(zeroesIndex);
//            return function(divideBy / 2, predicate.apply(onesIndex, zeroesIndex), list, predicate);
//        }
//        indexes.forEach(number -> {
//            System.out.println(list.get(number) + " " + list.get(number) / divideBy);
//            if (list.get(number) / divideBy >= 1) {
//                onesIndex.add(number);
//                list.r
//            } else {
//                zeroesIndex.add(number);
//            }
//        });
//        System.out.println(onesIndex);
//        System.out.println(zeroesIndex);
//        return function(divideBy / 2, predicate.apply(onesIndex, zeroesIndex), list, predicate);
//    }

    public static int oxygenRating(List<String> list, int index) {
        List<String> onesIndex = new ArrayList<>();
        List<String> zeroesIndex = new ArrayList<>();

        if (list.size() == 2 || (!list.isEmpty() && list.get(0).length() == index - 1)) {
            if ('1' == list.get(0).charAt(index)) {
                return Integer.parseInt(list.get(0), 2);
            } else if ('0' == list.get(1).charAt(index)) {
                return Integer.parseInt(list.get(0), 2);
            } else {
                return Integer.parseInt(list.get(1), 2);
            }
        }
        list.forEach(line -> {
            if ('1' == line.charAt(index)) {
                onesIndex.add(line);
            } else {
                zeroesIndex.add(line);
            }
        });
//        System.out.println(onesIndex);
//        System.out.println(zeroesIndex);
        return oxygenRating(onesIndex.size() >= zeroesIndex.size() ? onesIndex : zeroesIndex, index + 1);
    }

    public static int co2Rating(List<String> list, int index) {
        List<String> onesIndex = new ArrayList<>();
        List<String> zeroesIndex = new ArrayList<>();

        if (list.size() == 1 || (!list.isEmpty() && list.get(0).length() == index - 1)) {
//            if ('0' == list.get(0).charAt(index)) {
                return Integer.parseInt(list.get(0), 2);
//            } else if ('1' == list.get(1).charAt(index)) {
//                return Integer.parseInt(list.get(0), 2);
//            } else {
//                return Integer.parseInt(list.get(1), 2);
//            }
        }
        list.forEach(line -> {
            if ('1' == line.charAt(index)) {
                onesIndex.add(line);
            } else {
                zeroesIndex.add(line);
            }
        });
//        System.out.println(onesIndex);
//        System.out.println(zeroesIndex);
        return co2Rating(onesIndex.size() < zeroesIndex.size() ? onesIndex : zeroesIndex, index + 1);
    }
}



class Rate {
    public int ones = 0;
    public int zeros = 0;
}