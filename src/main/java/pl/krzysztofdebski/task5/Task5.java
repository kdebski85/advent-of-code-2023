package pl.krzysztofdebski.task5;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.readAllLines;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static pl.krzysztofdebski.utils.ParsingUtils.longs;
import static pl.krzysztofdebski.utils.Utils.partitionByNewLines;

public class Task5 {

    public static void main(String[] args) throws IOException {
        long result = Long.MAX_VALUE;

        List<List<String>> lists = partitionByNewLines(readAllLines(Path.of("src/main/resources/task5/5a-task.input")));
        List<Long> seeds = longs(lists.getFirst().getFirst());

        List<RangeMap<Long, Long>> maps = new ArrayList<>();

        for (List<String> lines : lists.subList(1, lists.size())) {
            RangeMap<Long, Long> map = TreeRangeMap.create();
            maps.add(map);

            for (String line : lines) {
                List<Long> longs = longs(line);
                if (!longs.isEmpty()) {
                    Long source = longs.get(1);
                    map.put(Range.closedOpen(source, source + longs.get(2)), source - longs.get(0));
                }
            }
        }

        for (Long seed : seeds) {
            long value = seed;
            for (RangeMap<Long, Long> map : maps) {
                Long l = map.get(value);
                if (l != null) {
                    value -= l;
                }
            }
            result = Math.min(result, value);
        }

        System.out.println(result);
    }
}
