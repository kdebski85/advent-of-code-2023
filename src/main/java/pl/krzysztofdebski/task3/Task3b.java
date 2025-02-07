package pl.krzysztofdebski.task3;

import pl.krzysztofdebski.utils.Coord;
import pl.krzysztofdebski.utils.Direction;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static java.lang.Character.isDigit;
import static pl.krzysztofdebski.utils.Utils.readTwoDimensionsCharArray;

public class Task3b {

    public static void main(String[] args) throws IOException {
        long result = 0;

        char[][] chars = readTwoDimensionsCharArray(Path.of("src/main/resources/task3/3a-task.input"));
        for (int y = 0; y < chars.length; y++) {
            char[] line = chars[y];
            for (int x = 0; x < line.length; x++) {
                if (line[x] == '*') {
                    Coord coord = new Coord(y, x);
                    Map<Coord, Integer> parts = new HashMap<>();
                    for (Direction direction : Direction.ALL_DIRECTIONS) {
                        Coord relative = coord.relative(direction);
                        Character c = relative.valueIfInBounds(chars);
                        if (c != null && isDigit(c)) {
                            int start = 0;
                            Character relativeChar;
                            while ((relativeChar = relative.relative(Direction.W, start).valueIfInBounds(chars)) != null && isDigit(relativeChar)) {
                                start++;
                            }
                            start--;

                            StringBuilder part = new StringBuilder();
                            int cur = start;

                            while ((relativeChar = relative.relative(Direction.W, cur).valueIfInBounds(chars)) != null && isDigit(relativeChar)) {
                                part.append(relativeChar);
                                cur--;
                            }

                            parts.put(relative.relative(Direction.W, start), Integer.parseInt(part.toString()));
                        }
                    }

                    if (parts.size() == 2) {
                        Iterator<Integer> iterator = parts.values().iterator();
                        result += (long) iterator.next() * iterator.next();
                    }
                }
            }
        }

        System.out.println(result);
    }
}
