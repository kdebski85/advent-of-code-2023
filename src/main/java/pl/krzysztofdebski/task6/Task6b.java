package pl.krzysztofdebski.task6;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.Files.readAllLines;
import static pl.krzysztofdebski.utils.ParsingUtils.longs;

public class Task6b {

    public static void main(String[] args) throws IOException {
        long result;

        List<String> lists = readAllLines(Path.of("src/main/resources/task6/6a-task.input"));
        List<Long> time = longs(lists.getFirst().replace(" ", ""));
        List<Long> distance = longs(lists.get(1).replace(" ", ""));

        long t = time.getFirst();
        long d = distance.getFirst();
        double delta = delta(1, -t, d);
        double lower = (t - delta) / 2d;
        double upper = (t + delta) / 2d;
        double floorUpper = Math.floor(upper);
        if (floorUpper == upper) {
            floorUpper--;
        }

        double ceilLower = Math.ceil(lower);
        if (ceilLower == lower) {
            ceilLower++;
        }

        result = (long) (floorUpper - ceilLower + 1);

        System.out.println(result);
    }

    private static double delta(long a, long b, long c) {
        return Math.sqrt(b * b - 4L * a * c);
    }
}
