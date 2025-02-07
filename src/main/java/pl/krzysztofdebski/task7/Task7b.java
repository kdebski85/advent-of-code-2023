package pl.krzysztofdebski.task7;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.nio.file.Files.readAllLines;

public class Task7b {

    private static final String CARDS = "AKQT98765432J";

    record Hand(String cards, int bid) {

    }

    public static void main(String[] args) throws IOException {
        long result = 0;
        List<String> lines = readAllLines(Path.of("src/main/resources/task7/7a-task.input"));
        List<Hand> hands = new ArrayList<>();
        for (String line : lines) {
            String[] s = line.split(" ");
            hands.add(new Hand(s[0], Integer.parseInt(s[1])));
        }
        hands.sort(new HandComparator());

        for (int i = 1; i <= hands.size(); i++) {
            result += (long) i * hands.get(i - 1).bid;
        }

        System.out.println(result); //252137472
    }

    private static class HandComparator implements Comparator<Hand> {

        @Override
        public int compare(Hand o1, Hand o2) {
            String c1 = o1.cards;
            String c2 = o2.cards;
            int diff = occurrences(c1).compareTo(occurrences(c2));
            if (diff != 0) {
                return diff;
            }

            for (int i = 0; i < c1.length(); i++) {
                diff = CARDS.indexOf(c2.charAt(i)) - CARDS.indexOf(c1.charAt(i));
                if (diff != 0) {
                    return diff;
                }
            }

            return 0;
        }
    }

    private static String occurrences(String cards) {
        long jokers = cards.chars()
                           .filter(c -> c == 'J')
                           .count();

        List<Long> values =
            new ArrayList<>(cards.chars()
                                 .filter(c -> c != 'J')
                                 .boxed()
                                 .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                                 .values());

        values.sort(Comparator.reverseOrder());

        if (values.isEmpty()) {
            values = List.of(jokers);
        } else {
            values.set(0, values.getFirst() + jokers);
        }


        return values
            .stream()
            .map(String::valueOf)
            .collect(Collectors.joining(""));
    }

}
