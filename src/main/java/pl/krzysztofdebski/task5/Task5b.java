package pl.krzysztofdebski.task5;

import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeMap;
import com.google.common.collect.TreeRangeSet;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import static java.nio.file.Files.readAllLines;
import static pl.krzysztofdebski.utils.ParsingUtils.longs;
import static pl.krzysztofdebski.utils.Utils.partitionByNewLines;

public class Task5b {

    public static void main(String[] args) throws IOException {
        long result;

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
                    map.put(Range.closedOpen(source, source + longs.get(2)), longs.get(0) - source);
                }
            }
        }

        RangeSet<Long> rangeSet = TreeRangeSet.create();

        for (List<Long> seed : Lists.partition(seeds, 2)) {
            Range<Long> seedRange = Range.closedOpen(seed.get(0), seed.get(0) + seed.get(1));
            rangeSet.add(seedRange);
        }

        for (RangeMap<Long, Long> map : maps) {
            RangeSet<Long> newRangeSet = TreeRangeSet.create();

            for (Range<Long> range : rangeSet.asRanges()) {
                long lower = range.lowerEndpoint();
                long upper = range.upperEndpoint();

                for (Entry<Range<Long>, Long> entry : map.asMapOfRanges().entrySet()) {
                    Range<Long> mappingRange = entry.getKey();
                    long mappingValue = entry.getValue();
                    long rangeLower = mappingRange.lowerEndpoint();
                    long rangeUpper = mappingRange.upperEndpoint();
                    if (lower < rangeLower) {
                        long upper2 = Math.min(rangeLower, upper);
                        newRangeSet.add(Range.closedOpen(lower, upper2));
                        lower = upper2;
                    }

                    if (lower >= upper - 1) {
                        break;
                    }

                    if (mappingRange.contains(lower)) {
                        long subRangeUpper = Math.min(rangeUpper, upper);
                        newRangeSet.add(Range.closedOpen(lower + mappingValue, subRangeUpper + mappingValue));
                        lower = subRangeUpper;
                    }
                }

                if (lower < upper - 1) {
                    newRangeSet.add(Range.closedOpen(lower, upper));
                }
            }

            rangeSet = newRangeSet;
        }

        result = rangeSet.asRanges().iterator().next().lowerEndpoint();
        System.out.println(result);
    }
}
