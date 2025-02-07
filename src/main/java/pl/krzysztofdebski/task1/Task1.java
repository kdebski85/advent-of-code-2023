package pl.krzysztofdebski.task1;

import java.io.IOException;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.file.Files.readAllLines;

public class Task1 {

    public static void main(String[] args) throws IOException {
        int result = 0;

        Pattern pattern = Pattern.compile("\\d");
        for (String line : readAllLines(Path.of("src/main/resources/task1/1a-task.input"))) {
            String first = null;
            String last = null;
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                last = matcher.group();
                if (first == null) {
                    first = last;
                }
            }
            if (first != null) {
                result += Integer.parseInt(first + last);
            }
        }

        System.out.println(result);
    }
}
