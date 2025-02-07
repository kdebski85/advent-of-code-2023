package pl.krzysztofdebski.task2;

import java.io.IOException;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.file.Files.readAllLines;

public class Task2b {

    public static void main(String[] args) throws IOException {
        int result = 0;

        Pattern pattern = Pattern.compile("(\\d+) (green|red|blue)");

        for (String line : readAllLines(Path.of("src/main/resources/task2/2a-task.input"))) {
            int green = 0;
            int red = 0;
            int blue = 0;
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                int d = Integer.parseInt(matcher.group(1));
                switch (matcher.group(2)) {
                    case "red" -> red = Math.max(red, d);
                    case "green" -> green = Math.max(green, d);
                    case "blue" -> blue = Math.max(blue, d);
                    default -> throw new IllegalArgumentException();
                }
            }

            result += green * red * blue;
        }

        System.out.println(result);
    }
}
