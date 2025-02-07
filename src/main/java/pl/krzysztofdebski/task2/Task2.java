package pl.krzysztofdebski.task2;

import java.io.IOException;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.file.Files.readAllLines;

public class Task2 {

    public static void main(String[] args) throws IOException {
        int result = 0;
//12 red cubes, 13 green cubes, and 14 blue cubes
        Pattern pattern = Pattern.compile("(\\d+) (green|red|blue)");
        int game = 0;
        main: for (String line : readAllLines(Path.of("src/main/resources/task2/2a-task.input"))) {
            game++;
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                int d = Integer.parseInt(matcher.group(1));
                int max = max(matcher.group(2));

                if (d > max) {
                    continue main;
                }
            }

            result += game;
        }

        System.out.println(result);
    }

    private static int max(String s) {
        return switch (s) {
            case "red" -> 12;
            case "green" -> 13;
            case "blue" -> 14;
            default -> throw new IllegalArgumentException();
        };
    }
}
