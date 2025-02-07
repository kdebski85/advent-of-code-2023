package pl.krzysztofdebski.task3;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import pl.krzysztofdebski.utils.Coord;
import pl.krzysztofdebski.utils.Direction;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static java.nio.file.Files.readAllLines;

public class Task3bRanges {


    private record NumberWithId(int number, int id) {

    }

    public static void main(String[] args) throws IOException {
        Pattern DIGITS = Pattern.compile("\\d+");

        long result = 0;
        AtomicInteger numberId = new AtomicInteger(0);

        List<String> chars = readAllLines(Path.of("src/main/resources/task3/3a-task.input"));

        @SuppressWarnings("unchecked")
        final RangeMap<Integer, NumberWithId>[] numbers = new RangeMap[chars.size()];
        for (int i = 0; i < chars.size(); i++) {
            numbers[i] = TreeRangeMap.create();
        }

        for (int y = 0; y < chars.size(); y++) {
            String line = chars.get(y);
            RangeMap<Integer, NumberWithId> numbersInLine = numbers[y];

            Matcher matcher = DIGITS.matcher(line);
            matcher.results()
                   .forEach(match -> numbersInLine.put(
                       Range.closedOpen(match.start(), match.end()),
                       new NumberWithId(Integer.parseInt(match.group()), numberId.getAndIncrement())));
        }

        for (int y = 0; y < chars.size(); y++) {
            String line = chars.get(y);

            List<Integer> starIndices = allIndexes(line, '*');

            for (Integer starIndex : starIndices) {
                Set<NumberWithId> parts = new HashSet<>();
                Coord coord = new Coord(y, starIndex);
                for (Direction direction : Direction.ALL_DIRECTIONS) {
                    Coord relative = coord.relative(direction);
                    NumberWithId numberWithId = numbers[relative.y()].get(relative.x());
                    if (numberWithId != null) {
                        parts.add(numberWithId);
                    }
                }
                if (parts.size() == 2) {
                    Iterator<NumberWithId> iterator = parts.iterator();
                    result += (long) iterator.next().number() * iterator.next().number();
                }
            }
        }

        System.out.println(result);
    }

    private static List<Integer> allIndexes(String string, char toFind) {
        return IntStream
            .iterate(string.indexOf(toFind), index -> index >= 0, index -> string.indexOf(toFind, index + 1))
            .boxed()
            .toList();
    }
}
