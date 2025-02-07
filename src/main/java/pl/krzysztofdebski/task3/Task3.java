package pl.krzysztofdebski.task3;

import pl.krzysztofdebski.utils.Coord;
import pl.krzysztofdebski.utils.Direction;

import java.io.IOException;
import java.nio.file.Path;

import static java.nio.file.Files.readAllLines;
import static pl.krzysztofdebski.utils.Utils.readTwoDimensionsCharArray;

public class Task3 {

    public static void main(String[] args) throws IOException {
        int result = 0;

        char[][] chars = readTwoDimensionsCharArray(Path.of("src/main/resources/task3/3a-sample.input"));
        for (int y = 0; y < chars.length; y++) {
            char[] line = chars[y];
            for (int x = 0; x < line.length; x++) {
                if (Character.isDigit(line[x])) {
                    boolean valid = false;
                    String s = "";

                    for (int x1 = x; x1 < line.length && Character.isDigit(line[x1]); x1++) {
                        s += line[x1];
                        Coord coord = new Coord(y, x1);
                        for (Direction direction : Direction.ALL_DIRECTIONS) {
                            Character c = coord.relative(direction).valueIfInBounds(chars);
                            if (c != null && !Character.isDigit(c) && c != '.') {
                                valid = true;
                            }
                        }
                        x = x1;
                    }

                    if (valid) {
                        System.out.println(s);
                        result += Integer.parseInt(s);
                    }
                }
            }
        }

        System.out.println(result);
    }
}
