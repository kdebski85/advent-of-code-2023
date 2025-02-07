package pl.krzysztofdebski.task6;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.Files.readAllLines;
import static pl.krzysztofdebski.utils.ParsingUtils.ints;

public class Task6 {

    public static void main(String[] args) throws IOException {
        long result = 1;

        List<String> lists = readAllLines(Path.of("src/main/resources/task6/6a-task.input"));
        List<Integer> time = ints(lists.getFirst());
        List<Integer> distance = ints(lists.get(1));

        for (int i = 0; i < time.size(); i++) {
            int t = time.get(i);
            int d = distance.get(i);
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

            result *= floorUpper - ceilLower + 1;
        }
        System.out.println(result);
    }

    private static double delta(int a, int b, int c) {
        return Math.sqrt(((long) b) * b - 4L * a * c);
    }
}
