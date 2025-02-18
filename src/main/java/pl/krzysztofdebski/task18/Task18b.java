package pl.krzysztofdebski.task18;

import pl.krzysztofdebski.utils.Coord;
import pl.krzysztofdebski.utils.Direction;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.file.Files.readAllLines;

public class Task18b {

    static final Pattern pattern = Pattern.compile("(.) (\\d+) \\(#(.{5})(.)\\)");

    public static void main(String[] args) throws IOException {
        List<String> lines = readAllLines(Path.of("src/main/resources/task18/18-task.input"));

        Coord coord = new Coord(0, 0);

        long result = 2;

        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            if (!matcher.find()) {
                throw new IllegalArgumentException();
            }

            String dir = matcher.group(4);
            int steps = Integer.parseInt(matcher.group(3), 16);
            Direction d = switch (dir) {
                case "3" -> Direction.N;
                case "1" -> Direction.S;
                case "2" -> Direction.W;
                case "0" -> Direction.E;
                default -> throw new IllegalArgumentException();
            };

            Coord relative = coord.relative(d, steps);
            result += (long) relative.y() * coord.x() - (long) relative.x() * coord.y();
            result += steps;

            coord = relative;
        }

        System.out.println(result / 2);
    }
}
