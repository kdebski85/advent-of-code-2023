package pl.krzysztofdebski.task4;

import com.google.common.collect.Sets;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.nio.file.Files.readAllLines;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static pl.krzysztofdebski.utils.ParsingUtils.ints;

public class Task4b {
    public static void main(String[] args) throws IOException {
        long result = 0;
        int board = 0;
        List<String> lines = readAllLines(Path.of("src/main/resources/task4/4a-task.input"));
        long[] duplicates = new long[lines.size()];

        for (String line : lines) {
            String[] split = substringAfter(line, ":").split("\\|");
            Set<Integer> winning = new HashSet<>(ints(split[0]));
            Set<Integer> bets = new HashSet<>(ints(split[1]));
            int wins = Sets.intersection(winning, bets).size();

            if (wins > 0) {
                long dups = duplicates[board];

                result += (1 + dups) * wins;
                for (int i = 0; i < wins; i++) {
                    duplicates[board + i + 1] += 1 + dups;
                }
            }

            board++;
        }

        result += board;
        System.out.println(result);
    }
}
