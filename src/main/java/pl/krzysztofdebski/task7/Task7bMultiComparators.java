package pl.krzysztofdebski.task7;

import pl.krzysztofdebski.utils.ListComparator;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.Files.readAllLines;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.collectingAndThen;

public class Task7bMultiComparators {

    private static final String CARDS = "AKQT98765432J";

    record Hand(String cards, int bid, List<Integer> occurrences) {

    }

    public static void main(String[] args) throws IOException {
        long result = 0;
        List<String> lines = readAllLines(Path.of("src/main/resources/task7/7a-task.input"));
        List<Hand> hands = new ArrayList<>();
        for (String line : lines) {
            String[] s = line.split(" ");
            hands.add(new Hand(s[0], Integer.parseInt(s[1]), occurrences(s[0])));
        }
        hands.sort(HAND_COMPARATOR);

        for (int i = 1; i <= hands.size(); i++) {
            result += (long) i * hands.get(i - 1).bid;
        }

        System.out.println(result);
    }

    private static final Comparator<Hand> HAND_COMPARATOR = new HandByTypeComparator().thenComparing(new HandByCardsComparator());

    private static class HandByTypeComparator implements Comparator<Hand> {

        @Override
        public int compare(Hand o1, Hand o2) {
            return ListComparator.INTEGER_INSTANCE.compare(o1.occurrences, o2.occurrences);
        }
    }

    private static class HandByCardsComparator implements Comparator<Hand> {

        @Override
        public int compare(Hand o1, Hand o2) {
            String c1 = o1.cards;
            String c2 = o2.cards;
            int diff;
            for (int i = 0; i < c1.length(); i++) {
                diff = CARDS.indexOf(c2.charAt(i)) - CARDS.indexOf(c1.charAt(i));
                if (diff != 0) {
                    return diff;
                }

            }
            return 0;
        }
    }

    private static List<Integer> occurrences(String cards) {
        int jokers = (int) cards.chars()
                                .filter(c -> c == 'J')
                                .count();

        List<Integer> values =
            new ArrayList<>(cards.chars()
                                 .filter(c -> c != 'J')
                                 .boxed()
                                 .collect(Collectors.groupingBy(identity(), collectingAndThen(Collectors.counting(), Long::intValue)))
                                 .values());

        values.sort(Comparator.reverseOrder());

        if (values.isEmpty()) {
            values = List.of(jokers);
        } else {
            values.set(0, values.getFirst() + jokers);
        }

        return values;
    }

}
