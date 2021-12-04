package day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Squid {

    public static final int INT = 5;

    public static void main(String[] args) throws IOException {

        Stream<String> lines = Files.lines(Paths.get("4_1_data"));

        List<String> input = lines.collect(Collectors.toList());
        List<Board> boards = new ArrayList<>();

        initBoards(input, boards);
        lines.close();

        int[] draws = Stream.of(input.get(0).split(","))
                .mapToInt(Integer::parseInt)
                .toArray();

        bingo(boards, draws);
//        System.out.println(boards);
    }

    private static void bingo(List<Board> boards, int[] draws) {
        int wonCount = 0;
        for (int draw : draws) {
            for (Board board: boards) {
                if (board.mark(draw)) {
                    if (wonCount == boards.size() - 1) {
                        return;
                    }
                    wonCount++;

//                    System.out.println(board);
//                    return;
                }
            }
        }
    }

    private static void initBoards(List<String> input, List<Board> boards) {
        for (int i = 2; i < input.size(); i += 6) {
            Board board = new Board();
            board.board = new ArrayList<>();

            for (int j = 0; j < INT; j++) {
//                System.out.println(input.get(i + j));
                List<Cell> cells = Stream.of(input.get(i + j).split(" "))
                        .filter(value -> !Objects.equals(value, ""))
//                        .peek(System.out::println)
                        .map(value -> {
                            try {
                                return new Cell(Integer.parseInt(value));
                            } catch (NumberFormatException e) {
                                return null;
                            }
                        })
                        .filter(Objects::nonNull)
                        .toList();
                board.board.add(cells);
//                System.out.println(cells);
            }
            boards.add(board);

        }
    }
}

class Board {

    public static final int INT = 5;
    public List<List<Cell>> board;

    boolean won = false;
    int[] columns = new int[5];
    int[] rows = new int[5];

    boolean mark(int draw) {
        if (won) {
            return false;
        }
        for (int i = 0; i < INT; i++) {
            for (int j = 0; j < INT; j++) {

                Cell cell = board.get(i).get(j);

                if (cell.number == draw) {
                    cell.marked = true;
                    columns[j] += 1;
                    rows[i] += 1;

                    if (columns[j] == 5 || rows[i] == 5) {
                        printScore(draw);
                        won = true;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    void printScore(int draw) {
        int unmarked = 0;
        for (List<Cell> row : board) {
            for (Cell cell: row) {
                if (!cell.marked) unmarked += cell.number;
            }
        }
        System.out.println(unmarked * draw);
    }
    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < INT; j++) {
                builder
                        .append(board.get(i).get(j).number)
                        .append(" ");

            }
            builder.append("\n");
        }
        builder.append("\n\n");
        return builder.toString();
    }
}

class Cell {
    public int number;
    public boolean marked = false;

    Cell(int num) {
        number = num;
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }
}
