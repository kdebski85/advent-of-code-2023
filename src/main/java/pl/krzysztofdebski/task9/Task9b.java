package pl.krzysztofdebski.task9;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.readAllLines;
import static pl.krzysztofdebski.utils.ParsingUtils.ints;

public class Task9b {

    public static void main(String[] args) throws IOException {
        long result = 0;
        List<String> lines = readAllLines(Path.of("src/main/resources/task9/9-task.input"));

        for (String line : lines) {
            List<Integer> ints = ints(line);
            boolean evenIteration = true;
            while(true) {
                result += (long) ints.getFirst() * (evenIteration ? 1 : -1);
                boolean allZero = true;
                List<Integer> newInts = new ArrayList<>();
                for (int i = 0; i < ints.size() - 1; i++) {
                    int diff = ints.get(i + 1) - ints.get(i);
                    newInts.add(diff);
                    if (diff != 0) {
                        allZero = false;
                    }
                }
                if (allZero) {
                    break;
                }
                evenIteration ^= true;
                ints = newInts;
            }
        }

        System.out.println(result);
    }
}
