package pl.krzysztofdebski.task4;

import com.google.common.collect.Sets;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static java.nio.file.Files.readAllLines;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static pl.krzysztofdebski.utils.ParsingUtils.ints;

public class Task4 {
    public static void main(String[] args) throws IOException {
        long result = 0;

        for (String line : readAllLines(Path.of("src/main/resources/task4/4a-task.input"))) {
            String[] split = substringAfter(line, ":").split("\\|");
            Set<Integer> winning = new HashSet<>(ints(split[0]));
            Set<Integer> bets = new HashSet<>(ints(split[1]));
            int wins = Sets.intersection(winning, bets).size();
            if (wins > 0) {
                result += 1L << (wins - 1);
            }
        }

        System.out.println(result);
    }
}
