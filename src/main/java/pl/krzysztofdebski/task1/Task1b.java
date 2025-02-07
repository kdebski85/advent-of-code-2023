package pl.krzysztofdebski.task1;

import java.io.IOException;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.file.Files.readAllLines;

public class Task1b {

    public static void main(String[] args) throws IOException {
        int result = 0;

        Pattern pattern = Pattern.compile("(?=(\\d|one|two|three|four|five|six|seven|eight|nine))");
        for (String line : readAllLines(Path.of("src/main/resources/task1/1a-task.input"))) {
            String first = null;
            String last = null;
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                last = matcher.group(1);
                if (first == null) {
                    first = last;
                }
            }
            if (first != null) {
                result += 10 * value(first) + value(last);
            }
        }

        System.out.println(result);
    }

    private static int value(String s) {
        return switch (s) {
            case "one" -> 1;
            case "two" -> 2;
            case "three" -> 3;
            case "four" -> 4;
            case "five" -> 5;
            case "six" -> 6;
            case "seven" -> 7;
            case "eight" -> 8;
            case "nine" -> 9;
            default -> Integer.parseInt(s);
        };
    }
}
