package pl.krzysztofdebski.task14;

import pl.krzysztofdebski.utils.Utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Task14b {

    public static void main(String[] args) throws IOException {
        char[][] chars = Utils.readTwoDimensionsCharArray(Path.of("src/main/resources/task14/14-task.input"));

        final int WARM_UP_ITERATIONS = 1000;
        final int SEQUENCE_ITERATIONS = 10;

        for (int i = 0; i < WARM_UP_ITERATIONS; i++) {
            move(chars);
        }

        List<Long> sequence = new ArrayList<>();

        for (int i = 0; i < SEQUENCE_ITERATIONS; i++) {
            move(chars);
            sequence.add(score(chars));
        }

        Deque<Long> deque = new ArrayDeque<>();

        for (int i = 0; i < SEQUENCE_ITERATIONS; i++) {
            move(chars);
            deque.add(score(chars));
        }

        int period = -1;

        for (int i = 0; i < 100; i++) {
            move(chars);
            deque.pollFirst();
            deque.addLast(score(chars));
            if (sequence.equals(new ArrayList<>(deque))) {
                period = i + 1 + SEQUENCE_ITERATIONS;
                break;
            }
        }

        if (period == -1) {
            throw new RuntimeException("Period not found");
        }

        int remaining = (1000000000 - WARM_UP_ITERATIONS - SEQUENCE_ITERATIONS - period) % period;
        for (int i = 0; i < remaining; i++) {
            move(chars);
        }

        System.out.println(score(chars));
    }

    private static long score(char[][] chars) {
        long result = 0;
        for (int y = 0; y < chars.length; y++) {
            for (int x = 0; x < chars[0].length; x++) {
                if (chars[y][x] == 'O') {
                    result += chars.length - y;
                }
            }
        }
        return result;
    }

    private static void move(char[][] chars) {
        moveN(chars);
        moveW(chars);
        moveS(chars);
        moveE(chars);
    }

    private static void moveN(char[][] chars) {
        int colls = chars[0].length;
        for (int y = 1; y < chars.length; y++) {
            for (int x = 0; x < colls; x++) {
                if (chars[y][x] == 'O') {
                    int y1 = y;
                    while (y1 > 0 && chars[y1 - 1][x] == '.') {
                        y1--;
                    }
                    swap(chars, x, y1, x, y);
                }
            }
        }
    }

    private static void moveS(char[][] chars) {
        int colls = chars[0].length;
        int rows = chars.length;
        for (int y = chars.length - 1; y >= 0; y--) {
            for (int x = 0; x < colls; x++) {
                if (chars[y][x] == 'O') {
                    int y1 = y;
                    while (y1 < rows - 1 && chars[y1 + 1][x] == '.') {
                        y1++;
                    }
                    swap(chars, x, y1, x, y);
                }
            }
        }
    }

    private static void moveE(char[][] chars) {
        int colls = chars[0].length;
        int rows = chars.length;
        for (int x = colls - 1; x >= 0; x--) {
            for (int y = 0; y < rows; y++) {
                if (chars[y][x] == 'O') {
                    int x1 = x;
                    while (x1 < colls - 1 && chars[y][x1 + 1] == '.') {
                        x1++;
                    }
                    swap(chars, x1, y, x, y);
                }
            }
        }
    }

    private static void moveW(char[][] chars) {
        int colls = chars[0].length;
        int rows = chars.length;
        for (int x = 0; x < colls; x++) {
            for (int y = 0; y < rows; y++) {
                if (chars[y][x] == 'O') {
                    int x1 = x;
                    while (x1 > 0 && chars[y][x1 - 1] == '.') {
                        x1--;
                    }
                    swap(chars, x1, y, x, y);
                }
            }
        }
    }

    private static void swap(char[][] chars, int xDest, int yDest, int xSource, int ySource) {
        if (xDest != xSource || yDest != ySource) {
            chars[yDest][xDest] = 'O';
            chars[ySource][xSource] = '.';
        }
    }
}
